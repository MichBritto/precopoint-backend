package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.dto.usuario.consumidor.UpdateConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consumidor")
public class ConsumidorController {
    @Autowired
    ConsumidorService consumidorService;

    @Transactional
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateConsumidor(@PathVariable(name = "id") int idConsumidor, @RequestBody UpdateConsumidorRequestDto updateConsumidorRequestDto){
        return consumidorService.updateConsumidor(idConsumidor, updateConsumidorRequestDto);
    }

    @Transactional
    @DeleteMapping("delete/{id}")
    ResponseEntity<?> deleteConsumidor(@PathVariable(name = "id") int idConsumidor){
        return consumidorService.deleteConsumidor(idConsumidor);
    }
}
