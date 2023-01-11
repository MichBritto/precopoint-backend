package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import org.springframework.http.ResponseEntity;


public interface ConsumidorService {

    ResponseEntity<StatusResponseDto> addConsumidor(ConsumidorRequestDto consumidor) throws Exception;
}
