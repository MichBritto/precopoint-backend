package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.usuario.consumidor.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.consumidor.ConsumidorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.consumidor.UpdateConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.exception.AlreadyExistsException;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Usuario;
import br.com.precopoint.PrecoPoint.repository.RoleRepository;
import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ConsumidorServiceImpl implements ConsumidorService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    StatusService cadastroStatusService;
    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<StatusResponseDto> addConsumidor(ConsumidorRequestDto consumidor) {
        ThreadContext.put("user",authenticationController.getUser());
        try {
            usuarioRepository.findByEmail(consumidor.getEmail()).ifPresent(consumidorAux ->{
                throw new AlreadyExistsException("E-mail '"+ consumidor.getEmail() +"' já registrado no sistema.");
            });
            usuarioRepository.save(consumidor.toConsumidor(roleRepository));
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
            List<Usuario> consumidorList = usuarioRepository.findAll();
            List<ConsumidorResponseDto> finalList = consumidorList.stream()
                    .map(consumidor -> modelMapper.map(consumidor, ConsumidorResponseDto.class)).toList();
            return ResponseEntity.ok(finalList);

        }catch (Exception e){
            throw new DefaultException("Erro ao pegar usuários: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateConsumidor(UpdateConsumidorRequestDto updateConsumidorRequestDto) {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            Usuario consumidor = usuarioRepository.findByEmail(updateConsumidorRequestDto.getEmail()).orElseThrow(
                    () -> new NotFoundException("Erro: usuário com email '"+ updateConsumidorRequestDto.getEmail() +"' não encontrado."));
            if(updateConsumidorRequestDto.getEndereco() != null && !updateConsumidorRequestDto.getEndereco().trim().isEmpty() ){
                consumidor.setCep(updateConsumidorRequestDto.getEndereco());
            }
            if(updateConsumidorRequestDto.getNome() != null && !updateConsumidorRequestDto.getNome().trim().isEmpty()){
                consumidor.setNome(updateConsumidorRequestDto.getNome());
            }
            if(updateConsumidorRequestDto.getSenha() != null && !updateConsumidorRequestDto.getSenha().trim().isEmpty()){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String senhaCrypt = passwordEncoder.encode(updateConsumidorRequestDto.getSenha());
                consumidor.setSenha(senhaCrypt);
            }
            if(updateConsumidorRequestDto.getStatusConta() != null){
                consumidor.setStatusConta(updateConsumidorRequestDto.getStatusConta());
            }
            consumidor.setAtualizadoEm(LocalDateTime.now());
            usuarioRepository.save(consumidor);
            ModelMapper modelMapper = new ModelMapper();
            log.info("Dados de usuário '"+ updateConsumidorRequestDto.getEmail() +"' atualizados com sucesso.");
            return ResponseEntity.ok(modelMapper.map(consumidor,ConsumidorResponseDto.class));
        }catch(NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }catch(Exception e) {
            throw new DefaultException("Erro ao atualizar usuário: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ConsumidorResponseDto> getConsumidor(String email) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Usuario consumidor = usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("Erro: usuário com email '"+ email +"' não encontrado."));
            ConsumidorResponseDto response = modelMapper.map(consumidor, ConsumidorResponseDto.class);
            return ResponseEntity.ok(response);
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException("Erro ao pegar usuário '"+ email +"': "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteConsumidor(int idConsumidor) {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            Usuario consumidor = usuarioRepository.findById(idConsumidor).orElseThrow(
                    () -> new NotFoundException("Erro: usuário de id '"+ idConsumidor +"' não encontrado"));
            usuarioRepository.delete(consumidor);
            log.info("Consumidor '{}' removido com sucesso",consumidor.getEmail());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException("Erro ao remover produto: "+ e.getMessage());
        }
    }

}
