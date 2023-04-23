package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;

public interface StatusService {

    StatusResponseDto usuarioStatusTrue();
    StatusResponseDto produtoStatusTrue();
    StatusResponseDto produtoRemovidoStatusTrue();
    StatusResponseDto produtoAtualizadoStatusTrue();
    StatusResponseDto listaStatusTrue();
    StatusResponseDto addProdutoListaTrue();

}
