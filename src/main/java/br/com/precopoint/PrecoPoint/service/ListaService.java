package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.lista.ListaDonoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ListaService {

    public ResponseEntity<StatusResponseDto> criarLsita(ListaDonoDto request);
    public ResponseEntity<StatusResponseDto> addProduto(ListaProdutoDto request);

    public ResponseEntity<List<?>> getListaConsumidor(ConsumidorResponseDto consumidor) throws Exception;
    public ResponseEntity<List<?>> getProdutosByLista(ListaRequestDto listaRequestDto) throws Exception;
    public ResponseEntity<?> getValorLista(ListaRequestDto listaRequestDto) throws Exception;
}
