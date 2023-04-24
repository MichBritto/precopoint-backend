package br.com.precopoint.PrecoPoint.controller;


import br.com.precopoint.PrecoPoint.dto.produto.*;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController{
    @Autowired
    ProdutoService produtoService;
    @PostMapping("addproduto")
    ResponseEntity<StatusResponseDto> addProduto(@Valid @RequestBody ProdutoRequestDto request) throws Exception {
        return produtoService.addProduto(request);
    }

    @DeleteMapping("deleteproduto")
    ResponseEntity<StatusResponseDto> removeProduto(@Valid @RequestBody FindProdutoRequestDto request) throws Exception {
        return produtoService.deleteProduto(request);
    }
    @PutMapping("updateproduto/{id}")
    public ResponseEntity<?> updateProduto(@PathVariable(value = "id") int produtoId, @RequestBody @Valid UpdateProdutoRequestDto produtoDetails) throws Exception {
        return produtoService.updateProduto(produtoId, produtoDetails);
    }
    @GetMapping("list-produto") //retorna todos os produtos sem ordenação
    ResponseEntity<List<ProdutoResponseDto>> getProduto(){
        return produtoService.getProduto();
    }

    @GetMapping("list-produto-asc") //retorna os produtos do menor preço para o maior
    ResponseEntity<List<ProdutoResponseDto>> getProdutoAsc() throws Exception {
        return produtoService.getProdutoAsc();
    }
    @PostMapping("list-produto-nome")//retorna a lista dos produtos em ordem de preco de acordo com o nome do produto
    ResponseEntity<List<ProdutoResponseDto>> getProdutoByNomeAsc(@Valid @RequestBody FindProdutoRequestDto findProdutoRequestDto) throws Exception {
        return produtoService.getProdutoByNomeAsc(findProdutoRequestDto);
    }


}
