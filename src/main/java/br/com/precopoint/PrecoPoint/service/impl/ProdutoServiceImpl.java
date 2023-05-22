package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.produto.*;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Categoria;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.model.Usuario;
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

    private ModelMapper modelMapper = new ModelMapper();

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
    public ResponseEntity<List<ProdutoResponseDto>> getProdutosByFornecedor(String email) {
        try{
            Usuario fornecedor = usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("Erro: usuário '"+ email +"' não encontrado"));
            List<ProdutoResponseDto> list = produtoRepository.findByFornecedor(fornecedor)
                    .stream()
                    .map(produto -> {
                        var produtoFornecedor = modelMapper.map(produto,ProdutoResponseDto.class);
                        produtoFornecedor.setFornecedor(fornecedor.getNome());
                        System.out.println("implementando push");
                        return produtoFornecedor;
                    }).toList();
            return ResponseEntity.ok(list);
        } catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<DistinctProdutoResponseDto>> getDistinctProduto() {
        List<DistinctProdutoResponseDto> list = produtoRepository.findDistinctProdutoDescricaoMarca().stream()
                .map(produto -> {
                    DistinctProdutoResponseDto produtoResponseDto = modelMapper.map(produto, DistinctProdutoResponseDto.class);
                    produtoResponseDto.setCategoria(produto.getCategoria().getCategoria());
                    return produtoResponseDto;
                }).toList();
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<List<DistinctProdutoResponseDto>> getProdutoByCategoria(int idCategoria) {
        try{
            Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow(
                    () -> new NotFoundException("Categoria com id '"+ idCategoria +"' não foi encontrada.")
            );
            List<DistinctProdutoResponseDto> lista = produtoRepository.findByCategoriaDistinct(categoria)
                    .stream()
                    .map(produto -> {
                        var produtoResponseDto= modelMapper.map(produto,DistinctProdutoResponseDto.class);
                        produtoResponseDto.setCategoria(produto.getCategoria().getCategoria());
                        return produtoResponseDto;
                    }).toList();
            return ResponseEntity.ok(lista);
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new DefaultException("Erro ao listar produtos por categoria: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ProdutoResponseDto>> getProdutoAsc() throws Exception {
        try{
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

            if(precoMin != null && precoMax != null && precoMin > precoMax){
                throw new DefaultException("'Preço Mínimo' deve ser maior que 'Preço Máximo'");
            }
            if((produto != null && !produto.isEmpty()) && precoMin != null && !precoMin.toString().isEmpty() && precoMax != null && !precoMax.toString().isEmpty()){
                listResponse = produtoRepository.findByProdutoAndPrecoBetween(produto,precoMin,precoMax)
                        .stream()
                        .map(produtoAux -> {
                            ProdutoResponseDto produtoResponseDto = modelMapper.map(produtoAux, ProdutoResponseDto.class);
                            produtoResponseDto.setFornecedor(produtoAux.getFornecedor().getNome());
                            produtoResponseDto.setCategoria(produtoAux.getCategoria().getCategoria());
                            return produtoResponseDto;
                        }).toList();
            }
            else if(precoMin != null && !precoMin.toString().isEmpty() && precoMax != null && !precoMax.toString().isEmpty()){
                listResponse = produtoRepository.findByPrecoBetween(precoMin, precoMax)
                        .stream()
                        .map(produtoAux -> {
                            ProdutoResponseDto produtoResponseDto = modelMapper.map(produtoAux, ProdutoResponseDto.class);
                            produtoResponseDto.setFornecedor(produtoAux.getFornecedor().getNome());
                            produtoResponseDto.setCategoria(produtoAux.getCategoria().getCategoria());
                            return produtoResponseDto;
                        }).toList();
            }
            else if((produto != null && !produto.isEmpty()) && precoMin != null && !precoMin.toString().isEmpty()){
                listResponse = produtoRepository.findByProdutoAndPrecoMin(produto,precoMin)
                        .stream()
                        .map(produtoAux -> {
                            ProdutoResponseDto produtoResponseDto = modelMapper.map(produtoAux, ProdutoResponseDto.class);
                            produtoResponseDto.setFornecedor(produtoAux.getFornecedor().getNome());
                            produtoResponseDto.setCategoria(produtoAux.getCategoria().getCategoria());
                            return produtoResponseDto;
                        }).toList();
            }
            else if((produto != null && !produto.isEmpty()) && precoMax != null && !precoMax.toString().isEmpty()){
                listResponse = produtoRepository.findByProdutoAndPrecoMax(produto,precoMax)
                        .stream()
                        .map(produtoAux -> {
                            ProdutoResponseDto produtoResponseDto = modelMapper.map(produtoAux, ProdutoResponseDto.class);
                            produtoResponseDto.setFornecedor(produtoAux.getFornecedor().getNome());
                            produtoResponseDto.setCategoria(produtoAux.getCategoria().getCategoria());
                            return produtoResponseDto;
                        }).toList();
            }
            else if(precoMin != null && !precoMin.toString().isEmpty()){
                listResponse = produtoRepository.findByPrecoMin(precoMin)
                        .stream()
                        .map(produtoAux -> {
                            ProdutoResponseDto produtoResponseDto = modelMapper.map(produtoAux, ProdutoResponseDto.class);
                            produtoResponseDto.setFornecedor(produtoAux.getFornecedor().getNome());
                            produtoResponseDto.setCategoria(produtoAux.getCategoria().getCategoria());
                            return produtoResponseDto;
                        }).toList();
            }
            else if(precoMax != null && !precoMax.toString().isEmpty()){
                listResponse = produtoRepository.findByPrecoMax(precoMax)
                        .stream()
                        .map(produtoAux -> {
                            ProdutoResponseDto produtoResponseDto = modelMapper.map(produtoAux, ProdutoResponseDto.class);
                            produtoResponseDto.setFornecedor(produtoAux.getFornecedor().getNome());
                            produtoResponseDto.setCategoria(produtoAux.getCategoria().getCategoria());
                            return produtoResponseDto;
                        }).toList();
            }
            else if(produto != null && !produto.isEmpty()){
                listResponse = produtoRepository.findByProduto(produto)
                        .stream()
                        .map(produtoAux -> {
                            ProdutoResponseDto produtoResponseDto = modelMapper.map(produtoAux, ProdutoResponseDto.class);
                            produtoResponseDto.setFornecedor(produtoAux.getFornecedor().getNome());
                            produtoResponseDto.setCategoria(produtoAux.getCategoria().getCategoria());
                            return produtoResponseDto;
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
