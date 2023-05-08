package br.com.precopoint.PrecoPoint.controller;


import br.com.precopoint.PrecoPoint.dto.lista.ListaDonoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaRequestDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListasDeConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.service.ListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lista")
public class ListaProdutoController {
    @Autowired
    ListaService listaService;

    @PostMapping("criarlista")
    public ResponseEntity<StatusResponseDto> criarLsita(@Valid @RequestBody ListaDonoDto request){
        return listaService.criarLsita(request);
    }

    @PostMapping("addproduto")
    public ResponseEntity<StatusResponseDto> addProduto(@Valid @RequestBody ListaProdutoDto request){
        return listaService.addProduto(request);
    }

    @PostMapping("getlista-consumidor")
    public ResponseEntity<List<?>> getListaConsumidor(@Valid @RequestBody ListasDeConsumidorRequestDto consumidor) throws Exception {
        return listaService.getListaConsumidor(consumidor);
    }

    @PostMapping("getprodutos-lista")
    public ResponseEntity<List<?>> getProdutosByLista(@Valid @RequestBody ListaRequestDto listaRequestDto) throws Exception {
        return listaService.getProdutosByLista(listaRequestDto);
    }

    @PostMapping("getvalortotal")
    public ResponseEntity<?> getValorLista(@Valid @RequestBody ListaRequestDto listaRequestDto) throws Exception {
        return listaService.getValorLista(listaRequestDto);
    }
}
