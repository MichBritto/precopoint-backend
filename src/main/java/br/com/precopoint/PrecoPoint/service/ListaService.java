package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.lista.ListaDonoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaRequestDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListasDeConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ListaService {

    ResponseEntity<StatusResponseDto> criarLsita(ListaDonoDto request);
    ResponseEntity<StatusResponseDto> addProduto(ListaProdutoDto request);
    ResponseEntity<List<?>> getListaConsumidor(ListasDeConsumidorRequestDto consumidor) throws Exception;
    ResponseEntity<List<?>> getProdutosByLista(ListaRequestDto listaRequestDto) throws Exception;
    ResponseEntity<?> getValorLista(ListaRequestDto listaRequestDto) throws Exception;
}
