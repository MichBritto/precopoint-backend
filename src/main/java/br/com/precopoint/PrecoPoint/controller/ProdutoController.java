package br.com.precopoint.PrecoPoint.controller;


import br.com.precopoint.PrecoPoint.dto.produto.ProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoResponseDto;
import br.com.precopoint.PrecoPoint.dto.produto.UpdateProdutoRequestDto;
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
    @PostMapping("add")
    ResponseEntity<StatusResponseDto> addProduto(@Valid @RequestBody ProdutoRequestDto request) throws Exception {
        return produtoService.addProduto(request);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity<?> deleteProduto(@PathVariable("id") int idProduto) throws Exception {
        return produtoService.deleteProduto(idProduto);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduto(@PathVariable(value = "id") int produtoId, @RequestBody @Valid UpdateProdutoRequestDto produtoDetails) throws Exception {
        return produtoService.updateProduto(produtoId, produtoDetails);
    }

    @GetMapping("list-produtos/{email}")
    public ResponseEntity<List<ProdutoResponseDto>> getProdutosByFornecedor(@PathVariable(name = "email") String email){
        return produtoService.getProdutosByFornecedor(email);
    }

}
