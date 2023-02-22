package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.produto.FindProdutoRequest;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.model.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ProdutoService {

    ResponseEntity<StatusResponseDto> addProduto(ProdutoRequestDto request) throws Exception;
    ResponseEntity<List<Produto>> getProduto();
    ResponseEntity<List<Produto>> getProdutoAsc() throws Exception;
    ResponseEntity<List<Produto>> getProdutoByNomeAsc(FindProdutoRequest findProdutoRequest) throws Exception;
}
