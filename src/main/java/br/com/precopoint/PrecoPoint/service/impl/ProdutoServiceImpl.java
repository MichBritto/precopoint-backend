package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.produto.*;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.repository.CategoriaRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.service.ProdutoService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProdutoServiceImpl implements ProdutoService {
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
        ThreadContext.put("user", authenticationController.getUser());
        try{
            produtoRepository.save(request.toProuduto(fornecedorRepository,categoriaRepository));

            log.info("Produto '{}' adicionado",request.getProduto());
            return ResponseEntity.ok(statusService.produtoStatusTrue());
        }catch(Exception e){
            throw new DefaultException("Erro ao adicionar produto: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<StatusResponseDto> deleteProduto(FindProdutoRequestDto request) throws Exception {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            Produto produto = produtoRepository.findById(Integer.parseInt(request.getProduto())).orElseThrow(
                    () -> new NotFoundException("Erro: produto '"+ request.getProduto() +"' não encontrado"));
           produtoRepository.delete(produto);
            log.info("Produto '{}' removido com sucesso",produto.getProduto());
            return ResponseEntity.ok(statusService.produtoRemovidoStatusTrue());
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException("Erro ao remover produto: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateProduto(int produtoId, UpdateProdutoRequestDto produtoDetails){
        try{
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado para este id: " + produtoId));

            if (produtoDetails.getProduto() != null) {
                produto.setProduto(produtoDetails.getProduto());
            }
            if (produtoDetails.getPreco() != 0.0) {
                produto.setPreco(produtoDetails.getPreco());
            }
            if (produtoDetails.getDescricao() != null) {
                produto.setDescricao(produtoDetails.getDescricao());
            }
            if(produtoDetails.getMarcaProduto() != null) {
                produto.setMarcaProduto(produtoDetails.getMarcaProduto());
            }
            if(produtoDetails.getCategoria() != null) {
                categoriaRepository.findByCategoria(produtoDetails.getCategoria()).ifPresent(produto::setCategoria);
            }
            if(produtoDetails.getFornecedor() != null) {
                fornecedorRepository.findByNome(produtoDetails.getFornecedor()).ifPresent(produto::setFornecedor);
            }
            if(produtoDetails.getImagem() != null) {
                produto.setImagem(produtoDetails.getImagem());
            }
            produtoRepository.save(produto);
            return ResponseEntity.ok(statusService.produtoAtualizadoStatusTrue());
        }catch(NotFoundException e) {
            throw new DefaultException(e.getMessage());
        }catch(Exception e) {
            throw new DefaultException("Erro ao atualizar produto: "+ e.getMessage());
        }
    }
    @Override
    public ResponseEntity<List<ProdutoResponseDto>> getProduto() {
        try{
            ModelMapper modelMapper = new ModelMapper();
            List<ProdutoResponseDto> list = produtoRepository.findAll().stream()
                    .map(produto -> {
                        ProdutoResponseDto produtoResponseDto = modelMapper.map(produto, ProdutoResponseDto.class);
                        produtoResponseDto.setFornecedor(produto.getFornecedor().getNome());
                        produtoResponseDto.setCategoria(produto.getCategoria().getCategoria());
                        return produtoResponseDto;
                    }).toList();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            throw new DefaultException("Erro ao pegar produtos: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ProdutoResponseDto>> getProdutoAsc() throws Exception {
        try{
            ModelMapper modelMapper = new ModelMapper();
            List<ProdutoResponseDto> list = produtoRepository.findAllByOrderByPrecoAsc().stream()
                    .map(produto -> {
                        ProdutoResponseDto produtoResponseDto = modelMapper.map(produto, ProdutoResponseDto.class);
                        produtoResponseDto.setFornecedor(produto.getFornecedor().getNome());
                        produtoResponseDto.setCategoria(produto.getCategoria().getCategoria());
                        return produtoResponseDto;
                    }).toList();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            throw new DefaultException("Erro ao pegar produtos: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ProdutoResponseDto>> getProdutoByNomeAsc(FindProdutoRequestDto findProdutoRequestDto) throws Exception {
        try{
            ModelMapper modelMapper = new ModelMapper();
            List<ProdutoResponseDto> list = produtoRepository.findAllByProdutoOrderByPrecoAsc(findProdutoRequestDto.getProduto()).stream()
                    .map(produto -> {
                        ProdutoResponseDto produtoResponseDto = modelMapper.map(produto, ProdutoResponseDto.class);
                        produtoResponseDto.setFornecedor(produto.getFornecedor().getNome());
                        produtoResponseDto.setCategoria(produto.getCategoria().getCategoria());
                        return produtoResponseDto;
                    }).toList();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            throw new DefaultException("Erro ao pegar produtos: "+ e.getMessage());
        }
    }
}
