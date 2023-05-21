package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.FornecedorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.UpdateFornecedorRequestDto;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fornecedor")
public class FornecedorController {
    @Autowired
    FornecedorService fornecedorService;

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
}
