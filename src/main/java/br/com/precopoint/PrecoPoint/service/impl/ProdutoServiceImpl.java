package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.dto.produto.ProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.service.ProdutoService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    StatusService statusService;

    @Override
    public ResponseEntity<StatusResponseDto> addProduto(ProdutoRequestDto request) throws Exception {

        try{
            produtoRepository.save(request.toProuduto(fornecedorRepository));
            return ResponseEntity.ok(statusService.produtoStatusTrue());
        }catch(Exception e){
            return ResponseEntity.ok(statusService.produtoStatusFalse());
        }

    }

    @Override
    public ResponseEntity<List<Produto>> getProduto() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }
}
