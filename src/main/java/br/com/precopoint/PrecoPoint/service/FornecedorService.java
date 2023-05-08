package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.filtro.FornecedorFilterByDistanceResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.FornecedorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.UpdateFornecedorRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface FornecedorService {
    ResponseEntity<StatusResponseDto> addFornecedor(FornecedorRequestDto fornecedor);
    ResponseEntity<?> updateFornecedor(int idFornecedor, UpdateFornecedorRequestDto updateFornecedorRequestDto);
    ResponseEntity<?> getAllFornecedor();
    ResponseEntity<?> deleteFornecedor(int idForncedor);
    ResponseEntity<List<FornecedorFilterByDistanceResponseDto>> getFornecedoresMaisProximos(String cep);
}
