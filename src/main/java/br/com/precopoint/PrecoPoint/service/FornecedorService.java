package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.usuario.FornecedorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import org.springframework.http.ResponseEntity;


public interface FornecedorService {
    ResponseEntity<StatusResponseDto> addFornecedor(FornecedorRequestDto fornecedor);
    ResponseEntity<?> getAllFornecedor();
}
