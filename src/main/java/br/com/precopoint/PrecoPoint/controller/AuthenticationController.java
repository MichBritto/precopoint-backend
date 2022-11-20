package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.config.security.TokenService;
import br.com.precopoint.PrecoPoint.dto.login.LoginFormDto;
import br.com.precopoint.PrecoPoint.dto.login.TokenDto;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController{

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    TokenService tokenService;



    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginFormDto login) {

        UsernamePasswordAuthenticationToken dadosLogin = login.converter();
        try{
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            TokenDto response = new TokenDto();
            response.setTipo("Bearer");
            response.setToken(token);
            return ResponseEntity.ok(response);
        }catch(AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }

    }
}
