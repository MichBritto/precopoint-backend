package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;

public interface StatusService {

    StatusResponseDto usuarioStatusTrue();
    StatusResponseDto usuarioStatusFalse();
    StatusResponseDto produtoStatusTrue();
    StatusResponseDto produtoStatusFalse();
    StatusResponseDto produtoRemovidoStatusTrue();
    StatusResponseDto produtoRemovidoStatusFalse();
    StatusResponseDto produtoAtualizadoStatusTrue();
    StatusResponseDto produtoAtualizadoStatusFalse();
    StatusResponseDto listaStatusTrue();
    StatusResponseDto listaStatusFalse();
    StatusResponseDto addProdutoListaTrue();
    StatusResponseDto addProdutoListaFalse();

}
