package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.lista.ListaDonoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import org.springframework.http.ResponseEntity;

public interface ListaService {

    public ResponseEntity<StatusResponseDto> criarLsita(ListaDonoDto request);
    public ResponseEntity<StatusResponseDto> addProduto(ListaProdutoDto request);
}
