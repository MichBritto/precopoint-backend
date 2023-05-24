package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.produto.*;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProdutoService {

    ResponseEntity<StatusResponseDto> addProduto(ProdutoRequestDto request) throws Exception;
    ResponseEntity<?> deleteProduto(int idProduto) throws Exception;
    ResponseEntity<?> updateProduto(int produtoId, UpdateProdutoRequestDto produtoDetails);
    ResponseEntity<List<ProdutoResponseDto>> getProduto();
    ResponseEntity<List<ProdutoResponseDto>> getProdutosByFornecedor(String email);
    ResponseEntity<List<DistinctProdutoResponseDto>> getDistinctProduto();
    ResponseEntity<List<DistinctProdutoResponseDto>> getProdutoByCategoria(int idCategoria);
    ResponseEntity<List<ProdutoResponseDto>> getProdutoAsc() throws Exception;
    ResponseEntity<List<ProdutoResponseDto>> getProdutoByNomeAsc(FindProdutoRequestDto findProdutoRequestDto) throws Exception;
    ResponseEntity<List<ProdutoResponseDto>> filterProdutos(String produto, String categoria, Double precoMin, Double precoMax);
}
