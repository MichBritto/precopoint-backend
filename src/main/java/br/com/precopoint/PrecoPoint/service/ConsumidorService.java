package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.usuario.consumidor.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.consumidor.UpdateConsumidorRequestDto;
import org.springframework.http.ResponseEntity;


public interface ConsumidorService {

    ResponseEntity<StatusResponseDto> addConsumidor(ConsumidorRequestDto consumidor);
    ResponseEntity<?> deleteConsumidor(int idConsumidor);
    ResponseEntity<?> getAllConsumidor();
    ResponseEntity<?> updateConsumidor(int idConsumidor, UpdateConsumidorRequestDto updateConsumidorRequestDto);
}
