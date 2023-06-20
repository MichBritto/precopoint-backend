package br.com.precopoint.PrecoPoint.controller;

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
    public ResponseEntity<?> getFornecedor(@PathVariable(name = "email") String email) {
        return fornecedorService.getFornecedor(email);
    }

    @Transactional
    @PutMapping("update")
    public ResponseEntity<?> updateFornecedor(@RequestBody UpdateFornecedorRequestDto updateFornecedorRequestDto){
        return fornecedorService.updateFornecedor(updateFornecedorRequestDto);
    }

    @Transactional
    @DeleteMapping("delete/{id}")
    ResponseEntity<?> deleteFornecedor(@PathVariable(name = "id") int idFornecedor){
        return fornecedorService.deleteFornecedor(idFornecedor);
    }
}
