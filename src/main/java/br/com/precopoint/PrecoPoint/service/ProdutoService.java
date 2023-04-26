package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.produto.FindProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoResponseDto;
import br.com.precopoint.PrecoPoint.dto.produto.UpdateProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProdutoService {

    ResponseEntity<StatusResponseDto> addProduto(ProdutoRequestDto request) throws Exception;
    ResponseEntity<StatusResponseDto> deleteProduto(FindProdutoRequestDto request) throws Exception;
    ResponseEntity<?> updateProduto(int produtoId, UpdateProdutoRequestDto produtoDetails);
    ResponseEntity<List<ProdutoResponseDto>> getProduto();
    ResponseEntity<List<ProdutoResponseDto>> getProdutoAsc() throws Exception;
    ResponseEntity<List<ProdutoResponseDto>> getProdutoByNomeAsc(FindProdutoRequestDto findProdutoRequestDto) throws Exception;
    ResponseEntity<List<ProdutoResponseDto>> filterProdutos(String produto, Double precoMin, Double precoMax);
}
