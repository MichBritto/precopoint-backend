package br.com.precopoint.PrecoPoint.service.impl;


import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.usuario.FornecedorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.FornecedorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.exception.AlreadyExistsException;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Fornecedor;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
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
public class FornecedorServiceImpl implements FornecedorService {
    @Autowired
    ConsumidorRepository consumidorRepository;
    @Autowired
    StatusService cadastroStatusService;
    @Autowired
    FornecedorRepository fornecedorRepository;
    @Autowired
    AuthenticationController authenticationController;

    @Override
    public ResponseEntity<StatusResponseDto> addFornecedor(FornecedorRequestDto fornecedor) {
        ThreadContext.put("user",authenticationController.getUser());
        try {
            consumidorRepository.findByEmail(fornecedor.getEmail()).ifPresent(consumidorAux ->{
                throw new AlreadyExistsException("E-mail '"+ fornecedor.getEmail() +"' já registrado no sistema.");
            });
            fornecedorRepository.findByEmail(fornecedor.getEmail()).ifPresent(fornecedorAux -> {
                throw new AlreadyExistsException("E-mail '"+ fornecedor.getEmail() +"' já registrado no sistema.");
            });
            fornecedorRepository.save(fornecedor.toFornecedor());
            log.info(cadastroStatusService.usuarioStatusTrue().getMensagem());
            return ResponseEntity.status(HttpStatus.CREATED).body(cadastroStatusService.usuarioStatusTrue());
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAllFornecedor() {
        try {
            ModelMapper modelMapper = new ModelMapper();
            List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
            List<FornecedorResponseDto> finalList = fornecedorList.stream()
                    .map(fornecedor -> modelMapper.map(fornecedor, FornecedorResponseDto.class)).toList();
            return ResponseEntity.ok(finalList);
        }catch (Exception e){
            throw new DefaultException("Erro ao pegar usuários: "+ e.getMessage());
        }
    }
}
