package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.UsuarioResponseDto;
import br.com.precopoint.PrecoPoint.model.Consumidor;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.service.CadastroStatusService;
import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsumidorServiceImpl implements ConsumidorService {

    @Autowired
    ConsumidorRepository consumidorRepository;
    @Autowired
    CadastroStatusService cadastroStatusService;
    @Autowired
    FornecedorRepository fornecedorRepository;

    @Override
    public ResponseEntity<UsuarioResponseDto> addConsumidor(Consumidor consumidor) throws Exception {

        try {
            if(consumidorRepository.findByEmail(consumidor.getEmail()).isEmpty()){
                if(fornecedorRepository.findByEmail(consumidor.getEmail()).isEmpty()){
                    consumidorRepository.save(consumidor);
                    return ResponseEntity.ok(cadastroStatusService.statusTrue());
                }

            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.ok(cadastroStatusService.statusFalse());
    }
}
