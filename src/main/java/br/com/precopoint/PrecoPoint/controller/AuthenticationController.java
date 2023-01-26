package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.config.security.TokenService;
import br.com.precopoint.PrecoPoint.dto.login.LoginFormDto;
import br.com.precopoint.PrecoPoint.dto.login.TokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    TokenService tokenService;

    LoginFormDto user;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginFormDto login) {
        this.user = login;
        UsernamePasswordAuthenticationToken dadosLogin = login.converter();
        try{
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            TokenDto response = new TokenDto();
            response.setTipo("Bearer");
            response.setToken(token);
            logger.info("Usuario "+ login.getEmail() +" logado com sucesso");
            return ResponseEntity.ok(response);
        }catch(AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }

    public LoginFormDto getUser(){
        return this.user;
    }
}
