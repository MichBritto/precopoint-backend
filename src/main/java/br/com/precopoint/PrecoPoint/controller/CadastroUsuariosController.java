package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.dto.usuario.consumidor.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.FornecedorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("cadastro")
public class CadastroUsuariosController {
    @Autowired
    ConsumidorService consumidorService;
    @Autowired
    FornecedorService fornecedorService;

    @PostMapping("consumidor")
    public ResponseEntity<StatusResponseDto> addConsumidor(@RequestBody @Valid ConsumidorRequestDto consumidor) {
        return consumidorService.addConsumidor(consumidor);
    }

    @PostMapping("fornecedor")
    public ResponseEntity<StatusResponseDto> addFornecedor(@RequestBody @Valid FornecedorRequestDto fornecedor) {
        return fornecedorService.addFornecedor(fornecedor);
    }
}
