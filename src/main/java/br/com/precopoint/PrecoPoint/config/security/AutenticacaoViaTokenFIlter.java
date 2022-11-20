package br.com.precopoint.PrecoPoint.config.security;

import br.com.precopoint.PrecoPoint.model.Usuario;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFIlter extends OncePerRequestFilter {

    private TokenService tokenService;
    private ConsumidorRepository consumidorRepository;
    private FornecedorRepository fornecedorRepository;

    public AutenticacaoViaTokenFIlter (TokenService tokenService, ConsumidorRepository consumidorRepository, FornecedorRepository fornecedorRepository){
        this.tokenService =  tokenService;
        this.consumidorRepository = consumidorRepository;
        this.fornecedorRepository = fornecedorRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = this.getToken(request);
        boolean valido = tokenService.isTokenValido(token);
        if(valido){
            autenticarCliente(token);
        }
        filterChain.doFilter(request,response);
    }

    private void autenticarCliente(String token) {

        String emailUsuario = this.tokenService.getEmailUsuario(token);
        Usuario usuario = null;
        if(fornecedorRepository.findByEmail(emailUsuario).isEmpty()){
            usuario = consumidorRepository.findByEmail(emailUsuario).get();

        }
        else
            usuario = fornecedorRepository.findByEmail(emailUsuario).get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}
