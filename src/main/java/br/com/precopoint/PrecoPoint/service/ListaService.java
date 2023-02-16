package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.lista.ListaDonoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.model.Lista;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ListaService {

    public ResponseEntity<StatusResponseDto> criarLsita(ListaDonoDto request);
    public ResponseEntity<StatusResponseDto> addProduto(ListaProdutoDto request);

    public ResponseEntity<List<?>> getListaConsumidor(ConsumidorResponseDto consumidor) throws Exception;
}
