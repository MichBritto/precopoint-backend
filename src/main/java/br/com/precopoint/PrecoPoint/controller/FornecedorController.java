package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.dto.produto.ProdutoResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.FornecedorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.UpdateFornecedorRequestDto;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import br.com.precopoint.PrecoPoint.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fornecedor")
public class FornecedorController {
    @Autowired
    FornecedorService fornecedorService;
    @Autowired
    ProdutoService produtoService;

    @GetMapping("get/{email}")
    public ResponseEntity<FornecedorResponseDto> getFornecedor(@PathVariable("email") String email){
        return fornecedorService.getFornecedor(email);
    }
    @Transactional
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateFornecedor(@PathVariable(name = "id") int idFornecedor, @RequestBody UpdateFornecedorRequestDto updateFornecedorRequestDto){
        return fornecedorService.updateFornecedor(idFornecedor, updateFornecedorRequestDto);
    }

    @Transactional
    @DeleteMapping("delete/{id}")
    ResponseEntity<?> deleteFornecedor(@PathVariable(name = "id") int idFornecedor){
        return fornecedorService.deleteFornecedor(idFornecedor);
    }

    @GetMapping("list-produtos/{email}")
    public ResponseEntity<List<ProdutoResponseDto>> getProdutosByFornecedor(@PathVariable(name = "email") String email){
        return produtoService.getProdutosByFornecedor(email);
    }
}
