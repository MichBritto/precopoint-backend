package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.produto.FindProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoResponseDto;
import br.com.precopoint.PrecoPoint.dto.produto.UpdateProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.repository.CategoriaRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
import br.com.precopoint.PrecoPoint.service.ProdutoService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProdutoServiceImpl implements ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    StatusService statusService;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public ResponseEntity<StatusResponseDto> addProduto(ProdutoRequestDto request) throws Exception {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            produtoRepository.save(request.toProuduto(usuarioRepository,categoriaRepository));

            log.info("Produto '{}' adicionado",request.getProduto());
            return ResponseEntity.ok(statusService.produtoStatusTrue());
        }catch(Exception e){
            throw new DefaultException("Erro ao adicionar produto: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteProduto(int idProduto) throws Exception {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            Produto produto = produtoRepository.findById(idProduto).orElseThrow(
                    () -> new NotFoundException("Erro: produto de id '"+ idProduto +"' não encontrado"));
           produtoRepository.delete(produto);
            log.info("Produto '{}' removido com sucesso.",produto.getProduto());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException("Erro ao remover produto: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateProduto(int produtoId, UpdateProdutoRequestDto produtoDetails){
        try{
            ModelMapper modelMapper = new ModelMapper();
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado para este id: " + produtoId));

            if (produtoDetails.getProduto() != null && !produtoDetails.getProduto().trim().isEmpty()) {
                produto.setProduto(produtoDetails.getProduto());
            }
            if (produtoDetails.getPreco() != 0.0) {
                produto.setPreco(produtoDetails.getPreco());
            }
            if (produtoDetails.getDescricao() != null && !produtoDetails.getDescricao().trim().isEmpty()) {
                produto.setDescricao(produtoDetails.getDescricao());
            }
            if(produtoDetails.getMarcaProduto() != null && !produtoDetails.getMarcaProduto().trim().isEmpty()) {
                produto.setMarcaProduto(produtoDetails.getMarcaProduto());
            }
            if(produtoDetails.getCategoria() != null && !produtoDetails.getCategoria().trim().isEmpty()) {
                categoriaRepository.findByCategoria(produtoDetails.getCategoria()).ifPresent(produto::setCategoria);
            }
            if(produtoDetails.getFornecedor() != null && !produtoDetails.getFornecedor().trim().isEmpty()) {
                usuarioRepository.findByNome(produtoDetails.getFornecedor()).ifPresent(produto::setFornecedor);
            }
            if(produtoDetails.getImagem() != null && !produtoDetails.getImagem().trim().isEmpty()) {
                produto.setImagem(produtoDetails.getImagem());
            }
            produtoRepository.save(produto);
            log.info("Produto de id '"+produto.getId()+"' atualizado com sucesso.");
            return ResponseEntity.ok(modelMapper.map(produto,ProdutoResponseDto.class));
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
            List<ProdutoResponseDto> list = produtoRepository.findAllByProdutoContainingIgnoreCaseOrderByPrecoAsc(findProdutoRequestDto.getProduto()).stream()
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
    public ResponseEntity<List<ProdutoResponseDto>> filterProdutos(String produto, Double precoMin, Double precoMax) {
        try{
            List<ProdutoResponseDto> listResponse = null;
            ModelMapper modelMapper = new ModelMapper();

            if(precoMin != null && precoMax != null && precoMin > precoMax){
                throw new DefaultException("'Preço Mínimo' deve ser maior que 'Preço Máximo'");
            }
            if((produto != null && !produto.isEmpty()) && precoMin != null  && precoMax != null){
                listResponse = produtoRepository.findByProdutoAndPrecoBetween(produto,precoMin,precoMax)
                        .stream()
                        .map(produtoAux -> {
                            return modelMapper.map(produtoAux, ProdutoResponseDto.class);
                        }).toList();
            }
            else if(precoMin != null  && precoMax != null){
                listResponse = produtoRepository.findByPrecoBetween(precoMin, precoMax)
                        .stream()
                        .map(produtoAux -> {
                            return modelMapper.map(produtoAux, ProdutoResponseDto.class);
                        }).toList();
            }
            else if((produto != null && !produto.isEmpty()) && precoMin != null){
                listResponse = produtoRepository.findByProdutoAndPrecoMin(produto,precoMin)
                        .stream()
                        .map(produtoAux -> {
                            return modelMapper.map(produtoAux, ProdutoResponseDto.class);
                        }).toList();
            }
            else if((produto != null && !produto.isEmpty()) && precoMax != null){
                listResponse = produtoRepository.findByProdutoAndPrecoMax(produto,precoMax)
                        .stream()
                        .map(produtoAux -> {
                            return modelMapper.map(produtoAux, ProdutoResponseDto.class);
                        }).toList();
            }
            else if(precoMin != null){
                listResponse = produtoRepository.findByPrecoMin(precoMin)
                        .stream()
                        .map(produtoAux -> {
                            return modelMapper.map(produtoAux, ProdutoResponseDto.class);
                        }).toList();
            }
            else if(precoMax != null){
                listResponse = produtoRepository.findByPrecoMax(precoMax)
                        .stream()
                        .map(produtoAux -> {
                            return modelMapper.map(produtoAux, ProdutoResponseDto.class);
                        }).toList();
            }
            else if(produto != null && !produto.isEmpty()){
                listResponse = produtoRepository.findAllByProdutoContainingIgnoreCaseOrderByPrecoAsc(produto)
                        .stream()
                        .map(produtoAux -> {
                            return modelMapper.map(produtoAux, ProdutoResponseDto.class);
                        }).toList();
            }
            else{
                throw new DefaultException("Pelo menos um parametro deve ser passado.");
            }
            return ResponseEntity.ok(listResponse);
        }catch(DefaultException e) {
            throw new DefaultException(e.getMessage());
        }catch(Exception e) {
            throw new DefaultException("Erro ao filtrar produtos: "+ e.getMessage());
        }
    }
}
