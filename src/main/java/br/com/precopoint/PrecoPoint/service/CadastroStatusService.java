package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.usuario.UsuarioResponseDto;

public interface CadastroStatusService {

    UsuarioResponseDto statusTrue();
    UsuarioResponseDto statusFalse();
}
