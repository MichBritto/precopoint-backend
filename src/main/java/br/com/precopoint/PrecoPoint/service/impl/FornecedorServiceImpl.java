package br.com.precopoint.PrecoPoint.service.impl;


import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.usuario.FornecedorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.service.StatusService;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    ConsumidorRepository consumidorRepository;
    @Autowired
    StatusService cadastroStatusService;
    @Autowired
    FornecedorRepository fornecedorRepository;

    @Override
    public ResponseEntity<StatusResponseDto> addFornecedor(FornecedorRequestDto fornecedor) throws Exception {

        try {
            if((consumidorRepository.findByEmail(fornecedor.getEmail()).isEmpty()) && (fornecedorRepository.findByEmail(fornecedor.getEmail()).isEmpty()) ){
                fornecedorRepository.save(fornecedor.toFornecedor());
                ThreadContext.put("user",fornecedor.getEmail());
                logger.info(cadastroStatusService.usuarioStatusTrue().getMensagem());
                return ResponseEntity.ok(cadastroStatusService.usuarioStatusTrue());
            }
        }catch(Exception e){
            logger.error("Exception: "+ e.getMessage());
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.badRequest().body(cadastroStatusService.usuarioStatusFalse());
    }
}
