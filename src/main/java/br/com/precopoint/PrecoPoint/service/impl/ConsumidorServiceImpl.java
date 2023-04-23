package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.exception.AlreadyExistsException;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Consumidor;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConsumidorServiceImpl implements ConsumidorService {
    @Autowired
    ConsumidorRepository consumidorRepository;
    @Autowired
    StatusService cadastroStatusService;
    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Override
    public ResponseEntity<StatusResponseDto> addConsumidor(ConsumidorRequestDto consumidor) {
        ThreadContext.put("user",authenticationController.getUser());
        try {
            consumidorRepository.findByEmail(consumidor.getEmail()).ifPresent(consumidorAux ->{
                throw new AlreadyExistsException("E-mail '"+ consumidor.getEmail() +"' já registrado no sistema.");
            });
            fornecedorRepository.findByEmail(consumidor.getEmail()).ifPresent(fornecedor -> {
                throw new AlreadyExistsException("E-mail '"+ consumidor.getEmail() +"' já registrado no sistema.");
            });
            consumidorRepository.save(consumidor.toConsumidor());
            log.info(cadastroStatusService.usuarioStatusTrue().getMensagem());
            return ResponseEntity.status(HttpStatus.CREATED).body(cadastroStatusService.usuarioStatusTrue());
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAllConsumidor() {
        try {
            ModelMapper modelMapper = new ModelMapper();
            List<Consumidor> consumidorList = consumidorRepository.findAll();
            List<ConsumidorResponseDto> finalList = consumidorList.stream()
                    .map(consumidor -> modelMapper.map(consumidor, ConsumidorResponseDto.class)).toList();
            return ResponseEntity.ok(finalList);

        }catch (Exception e){
            throw new DefaultException("Erro ao pegar usuários: "+ e.getMessage());
        }
    }


}
