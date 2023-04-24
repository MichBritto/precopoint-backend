package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.FornecedorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.UpdateConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("usuario")
public class UsuarioController {


    @Autowired
    ConsumidorService consumidorService;
    @Autowired
    FornecedorService fornecedorService;

    @PostMapping("add-consumidor")
    public ResponseEntity<StatusResponseDto> addConsumidor(@RequestBody @Valid ConsumidorRequestDto consumidor) throws Exception {
        return consumidorService.addConsumidor(consumidor);
    }


    @PostMapping("add-fornecedor")
    public ResponseEntity<StatusResponseDto> addFornecedor(@RequestBody @Valid FornecedorRequestDto fornecedor) throws Exception{
        return fornecedorService.addFornecedor(fornecedor);
    }

    @GetMapping("get-all-consumidor")
    public ResponseEntity<?> getAllConsumidor(){
        return consumidorService.getAllConsumidor();
    }

    @GetMapping("get-all-fornecedor")
    public ResponseEntity<?> getAllFornecedor(){
        return fornecedorService.getAllFornecedor();
    }

    @Transactional
    @PutMapping("update-consumidor/{id}")
    public ResponseEntity<?> updateConsumidor(@PathVariable(name = "id") int idConsumidor,@RequestBody UpdateConsumidorRequestDto updateConsumidorRequestDto){
        return consumidorService.updateConsumidor(idConsumidor, updateConsumidorRequestDto);
    }
}
