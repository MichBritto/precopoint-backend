package br.com.precopoint.PrecoPoint.service.impl;


import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.UsuarioResponseDto;
import br.com.precopoint.PrecoPoint.model.Fornecedor;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    ConsumidorRepository consumidorRepository;
    @Autowired
    StatusResponseDto statusResponseDto;
    @Autowired
    FornecedorRepository fornecedorRepository;

    @Override
    public ResponseEntity<UsuarioResponseDto> addFornecedor(Fornecedor fornecedor) throws Exception {

        try {
            if(consumidorRepository.findByEmail(fornecedor.getEmail()).isEmpty()){
                if(fornecedorRepository.findByEmail(fornecedor.getEmail()).isEmpty()){
                    fornecedorRepository.save(fornecedor);
                    return ResponseEntity.ok(statusResponseDto.statusTrue());
                }
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.ok(statusResponseDto.statusFalse());
    }
}
