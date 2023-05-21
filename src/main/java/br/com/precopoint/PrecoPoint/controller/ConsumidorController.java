package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.dto.usuario.consumidor.ConsumidorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.consumidor.UpdateConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("consumidor")
public class ConsumidorController {
    @Autowired
    ConsumidorService consumidorService;

    @GetMapping("get/{email}")
    public ResponseEntity<ConsumidorResponseDto> getConsumidor(@PathVariable(name = "email") String email){
        return consumidorService.getConsumidor(email);
    }
    @Transactional
    @PutMapping("update")
    public ResponseEntity<?> updateConsumidor(@Valid @RequestBody UpdateConsumidorRequestDto updateConsumidorRequestDto){
        return consumidorService.updateConsumidor(updateConsumidorRequestDto);
    }

    @Transactional
    @DeleteMapping("delete/{id}")
    ResponseEntity<?> deleteConsumidor(@PathVariable(name = "id") int idConsumidor){
        return consumidorService.deleteConsumidor(idConsumidor);
    }
}
