package br.com.precopoint.PrecoPoint.controller;


import br.com.precopoint.PrecoPoint.dto.produto.FindProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoResponseDto;
import br.com.precopoint.PrecoPoint.dto.produto.UpdateProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.service.ProdutoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger = LogManager.getLogger(ProdutoController.class);
    @PostMapping("addproduto")
    ResponseEntity<StatusResponseDto> addProduto(@Valid @RequestBody ProdutoRequestDto request) throws Exception {
        return produtoService.addProduto(request);
    }

    @DeleteMapping("deleteproduto")
    ResponseEntity<StatusResponseDto> removeProduto(@Valid @RequestBody FindProdutoRequestDto request) throws Exception {
        return produtoService.deleteProduto(request);
    }

    @PostMapping("updateproduto")
    public ResponseEntity<StatusResponseDto> updateProduto(@RequestBody @Valid UpdateProdutoRequestDto request) throws Exception {
        return produtoService.updateProduto(request);
    }
    @GetMapping("list-produto") //retorna todos os produtos sem ordenação
    ResponseEntity<List<ProdutoResponseDto>> getProduto(){
        return produtoService.getProduto();
    }

    @GetMapping("list-produto-asc") //retorna os produtos do menor preço para o maior
    ResponseEntity<List<ProdutoResponseDto>> getProdutoAsc() throws Exception {
        return produtoService.getProdutoAsc();
    }
    @PostMapping("list-produto-nome")//retorna a lista dos produtos em ordem de preco por nome
    ResponseEntity<List<ProdutoResponseDto>> getProdutoByNomeAsc(@Valid @RequestBody FindProdutoRequestDto findProdutoRequestDto) throws Exception {
        return produtoService.getProdutoByNomeAsc(findProdutoRequestDto);
    }


}
