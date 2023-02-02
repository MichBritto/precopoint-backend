package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.service.StatusService;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsumidorServiceImpl implements ConsumidorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    ConsumidorRepository consumidorRepository;
    @Autowired
    StatusService cadastroStatusService;
    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Override
    public ResponseEntity<StatusResponseDto> addConsumidor(ConsumidorRequestDto consumidor) throws Exception {

        try {
            if((consumidorRepository.findByEmail(consumidor.getEmail()).isEmpty()) && (fornecedorRepository.findByEmail(consumidor.getEmail()).isEmpty()) ){
                    consumidorRepository.save(consumidor.toConsumidor());
                    ThreadContext.put("user",authenticationController.getUser());
                    logger.info(cadastroStatusService.usuarioStatusTrue().getMensagem());
                    return ResponseEntity.ok(cadastroStatusService.usuarioStatusTrue());
            }
        }catch(Exception e){
            logger.info("Exception "+ e.getMessage());
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.badRequest().body(cadastroStatusService.usuarioStatusFalse());
    }
}
