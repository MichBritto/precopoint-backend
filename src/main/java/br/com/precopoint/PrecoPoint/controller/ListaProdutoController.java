package br.com.precopoint.PrecoPoint.controller;


import br.com.precopoint.PrecoPoint.dto.lista.ListaDonoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaRequestDto;
import br.com.precopoint.PrecoPoint.dto.lista.ValorTotalResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.repository.ListaProdutoRepository;
import br.com.precopoint.PrecoPoint.repository.ListaRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.service.ListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lista")
public class ListaProdutoController {

    @Autowired
    ListaService listaService;

    @Autowired
    ListaProdutoRepository listaProdutoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ListaRepository listaRepository;

    @PostMapping("criarlista")
    public ResponseEntity<StatusResponseDto> criarLsita(@Valid @RequestBody ListaDonoDto request){

        return listaService.criarLsita(request);
    }

    @PostMapping("addproduto")
    public ResponseEntity<StatusResponseDto> addProduto(@Valid @RequestBody ListaProdutoDto request){
        return listaService.addProduto(request);
    }

    @PostMapping("getlista-consumidor")
    public ResponseEntity<List<?>> getListaConsumidor(@Valid @RequestBody ConsumidorResponseDto consumidor, String lista) throws Exception {
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
