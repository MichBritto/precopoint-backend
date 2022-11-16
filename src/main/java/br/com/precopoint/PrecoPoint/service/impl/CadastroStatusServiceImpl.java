package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.dto.usuario.UsuarioResponseDto;
import br.com.precopoint.PrecoPoint.service.CadastroStatusService;
import org.springframework.stereotype.Service;

@Service
public class CadastroStatusServiceImpl implements CadastroStatusService {

    UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();

    @Override
    public UsuarioResponseDto statusTrue() {
        usuarioResponseDto.setMensagem("Cadastramento realizado com sucesso.");
        usuarioResponseDto.setStatus(true);
        return usuarioResponseDto;
    }

    @Override
    public UsuarioResponseDto statusFalse() {
        usuarioResponseDto.setMensagem("Falha no cadastramento, revise os dados e tente novamente.");
        usuarioResponseDto.setStatus(false);
        return usuarioResponseDto;
    }
}
