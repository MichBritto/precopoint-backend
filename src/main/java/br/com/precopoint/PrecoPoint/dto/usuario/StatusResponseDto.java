package br.com.precopoint.PrecoPoint.dto.usuario;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
public class StatusResponseDto {


    public UsuarioResponseDto statusTrue(){

        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setMensagem("Cadastramento realizado com sucesso.");
        usuarioResponseDto.setStatus(true);
        return usuarioResponseDto;
    }

    public UsuarioResponseDto statusFalse(){

        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setMensagem("Falha no cadastramento, revise os dados e tente novamente.");
        usuarioResponseDto.setStatus(false);
        return usuarioResponseDto;
    }




}
