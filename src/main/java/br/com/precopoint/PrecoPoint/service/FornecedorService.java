package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.filtro.FornecedorFilterByDistanceResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.FornecedorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.FornecedorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.UpdateFornecedorRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface FornecedorService {
    ResponseEntity<StatusResponseDto> addFornecedor(FornecedorRequestDto fornecedor);
    ResponseEntity<?> updateFornecedor(UpdateFornecedorRequestDto updateFornecedorRequestDto);
    ResponseEntity<?> getAllFornecedor();
    ResponseEntity<FornecedorResponseDto> getFornecedor(String email);
    ResponseEntity<?> deleteFornecedor(int idForncedor);
    ResponseEntity<List<FornecedorFilterByDistanceResponseDto>> getFornecedoresMaisProximos(String cep);
}
