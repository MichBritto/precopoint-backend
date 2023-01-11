package br.com.precopoint.PrecoPoint.service.impl;


import br.com.precopoint.PrecoPoint.dto.usuario.FornecedorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.service.StatusService;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FornecedorServiceImpl implements FornecedorService {

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
                return ResponseEntity.ok(cadastroStatusService.usuarioStatusTrue());
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.ok(cadastroStatusService.usuarioStatusFalse());
    }
}
