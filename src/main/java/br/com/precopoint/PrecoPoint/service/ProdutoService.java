package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.produto.ProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ProdutoService {

    ResponseEntity<StatusResponseDto> addProduto(ProdutoRequestDto request) throws Exception;
}
