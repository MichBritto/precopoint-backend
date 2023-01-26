package br.com.precopoint.PrecoPoint.controller;


import br.com.precopoint.PrecoPoint.PrecoPointApplication;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.service.ProdutoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ProdutoService produtoService;

    private static Logger logger = LogManager.getLogger(ProdutoController.class);
    @PostMapping("addproduto")
    ResponseEntity<StatusResponseDto> addProduto(@Valid @RequestBody ProdutoRequestDto request) throws Exception {
        return produtoService.addProduto(request);
    }

    @GetMapping("list-produto")
    ResponseEntity<List<Produto>> getProduto(){
        logger.info("Lista de produtos coletada");
        return produtoService.getProduto();
    }
}
