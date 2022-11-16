package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.dto.usuario.UsuarioResponseDto;
import br.com.precopoint.PrecoPoint.model.Consumidor;
import br.com.precopoint.PrecoPoint.model.Fornecedor;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("add-usuario")
public class UsuarioController {


    @Autowired
    ConsumidorService consumidorService;
    @Autowired
    FornecedorService fornecedorService;
    @PostMapping("consumidor")
    public ResponseEntity<UsuarioResponseDto> addConsumidor(@RequestBody @Valid Consumidor consumidor) throws Exception {
        return consumidorService.addConsumidor(consumidor);
    }


    @PostMapping("fornecedor")
    public ResponseEntity<UsuarioResponseDto> addFornecedor(@RequestBody @Valid Fornecedor fornecedor) throws Exception{
        return fornecedorService.addFornecedor(fornecedor);
    }
}
