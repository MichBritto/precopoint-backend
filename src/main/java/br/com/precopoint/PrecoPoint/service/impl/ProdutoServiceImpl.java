package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.produto.FindProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.UpdateProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.repository.CategoriaRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.service.ProdutoService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    StatusService statusService;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public ResponseEntity<StatusResponseDto> addProduto(ProdutoRequestDto request) throws Exception {
        try{
            produtoRepository.save(request.toProuduto(fornecedorRepository,categoriaRepository));
            ThreadContext.put("user", authenticationController.getUser());
            logger.info("Produto: "+request.getProduto() +" adicionado");
            return ResponseEntity.ok(statusService.produtoStatusTrue());
        }catch(Exception e){
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.produtoStatusFalse().getMensagem());
            return ResponseEntity.badRequest().body(statusService.produtoStatusFalse());
        }
    }

    @Override
    public ResponseEntity<StatusResponseDto> deleteProduto(FindProdutoRequestDto request) throws Exception {
        try{
            Produto produto = produtoRepository.findById(Integer.parseInt(request.getProduto())).get();
           produtoRepository.delete(produto);
            ThreadContext.put("user", authenticationController.getUser());
            logger.info("Produto: "+produto.getProduto() +" removido");
            return ResponseEntity.ok(statusService.produtoRemovidoStatusTrue());
        }catch(Exception e){
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.produtoStatusFalse().getMensagem());
            return ResponseEntity.badRequest().body(statusService.produtoRemovidoStatusFalse());
        }
    }

    @Override
    public ResponseEntity<StatusResponseDto> updateProduto(UpdateProdutoRequestDto request) throws Exception {
        try{
            Produto produto = produtoRepository.findById(Integer.parseInt(request.getFindProdutoRequestDto().getProduto())).get();
            produto.setProduto(request.getProdutoRequestDto().getProduto());
            produto.setMarcaProduto(request.getProdutoRequestDto().getMarcaProduto());
            produto.setCategoria(categoriaRepository.findById(Integer.parseInt(request.getProdutoRequestDto().getCategoria())).get());
            produto.setPreco(request.getProdutoRequestDto().getPreco());
            produto.setImagem(request.getProdutoRequestDto().getImagem());
            produto.setFornecedor(fornecedorRepository.findById(Integer.parseInt(request.getProdutoRequestDto().getFornecedor())).get());
            produtoRepository.save(produto);
            ThreadContext.put("user", authenticationController.getUser());
            logger.info("Produto Id: "+produto.getId() +" atualizado");
            return ResponseEntity.ok(statusService.produtoAtualizadoStatusTrue());
        }catch(Exception e){
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.produtoStatusFalse().getMensagem());
            return ResponseEntity.badRequest().body(statusService.produtoAtualizadoStatusFalse());
        }
    }

    @Override
    public ResponseEntity<List<Produto>> getProduto() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    @Override
    public ResponseEntity<List<Produto>> getProdutoAsc() throws Exception {
        try {
            return ResponseEntity.ok(produtoRepository.findAllByOrderByPrecoAsc());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Produto>> getProdutoByNomeAsc(FindProdutoRequestDto findProdutoRequestDto) throws Exception {
        try {
            return ResponseEntity.ok(produtoRepository.findAllByProdutoOrderByPrecoAsc(findProdutoRequestDto.getProduto()));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
