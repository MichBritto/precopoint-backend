package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.config.security.TokenService;
import br.com.precopoint.PrecoPoint.dto.login.LoginFormDto;
import br.com.precopoint.PrecoPoint.dto.login.TokenDto;
import br.com.precopoint.PrecoPoint.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
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
@Slf4j
public class AuthenticationController {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    TokenService tokenService;

    private String user;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginFormDto login) {
        this.user = login.getEmail();
        UsernamePasswordAuthenticationToken dadosLogin = login.converter();
        try{
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            TokenDto response = new TokenDto();
            response.setTipo("Bearer");
            response.setToken(token);
            ThreadContext.put("user",login.getEmail());
            log.info("Usuario logado com sucesso");
            return ResponseEntity.ok(response);
        }catch(AuthenticationException e){
            throw new UnauthorizedException("Credenciais Invalidas");
        }
    }

    public String getUser(){
        return this.user;
    }

}
