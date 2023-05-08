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
        try{
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getEmail(),login.getSenha()));
            ThreadContext.put("user",login.getEmail());
            log.info("Usuario logado com sucesso");
            return ResponseEntity.ok(new TokenDto(tokenService.gerarToken(authentication),"Bearer"));
        }catch(AuthenticationException e){
            throw new UnauthorizedException("Credenciais Invalidas");
        }
    }

    public String getUser(){
        return this.user;
    }

}