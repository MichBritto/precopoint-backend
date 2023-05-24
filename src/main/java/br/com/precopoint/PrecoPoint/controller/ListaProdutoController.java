package br.com.precopoint.PrecoPoint.controller;


import br.com.precopoint.PrecoPoint.dto.lista.CriarListaRequestDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.service.ListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lista")
public class ListaProdutoController {
    @Autowired
    ListaService listaService;

    @PostMapping("criarlista")
    public ResponseEntity<StatusResponseDto> criarLsita(@Valid @RequestBody CriarListaRequestDto request){
        return listaService.criarLsita(request);
    }

    @PostMapping("addproduto")
    public ResponseEntity<?> addProduto(@Valid @RequestBody ListaProdutoDto request){
        return listaService.addProduto(request);
    }

    @GetMapping("getlista-consumidor/{email}")
    public ResponseEntity<List<?>> getListaConsumidor(@PathVariable("email") String email) throws Exception {
        return listaService.getListaConsumidor(email);
    }

    @GetMapping("getprodutos-lista/{idLista}")
    public ResponseEntity<List<?>> getProdutosByLista(@PathVariable("idLista") int idLista) throws Exception {
        return listaService.getProdutosByLista(idLista);
    }

    @GetMapping("getvalortotal/{idLista}")
    public ResponseEntity<?> getValorLista(@PathVariable("idLista") int idLista) throws Exception {
        return listaService.getValorLista(idLista);
    }
}
