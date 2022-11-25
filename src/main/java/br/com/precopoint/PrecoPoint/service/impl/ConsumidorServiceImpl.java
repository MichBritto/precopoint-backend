package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.service.StatusService;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsumidorServiceImpl implements ConsumidorService {

    @Autowired
    ConsumidorRepository consumidorRepository;
    @Autowired
    StatusService cadastroStatusService;
    @Autowired
    FornecedorRepository fornecedorRepository;

    @Override
    public ResponseEntity<StatusResponseDto> addConsumidor(ConsumidorRequestDto consumidor) throws Exception {

        try {
            if((consumidorRepository.findByEmail(consumidor.getEmail()).isEmpty()) && (fornecedorRepository.findByEmail(consumidor.getEmail()).isEmpty()) ){
                    consumidorRepository.save(consumidor.toConsumidor());
                    return ResponseEntity.ok(cadastroStatusService.usuarioStatusTrue());
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.ok(cadastroStatusService.usuarioStatusFalse());
    }
}
