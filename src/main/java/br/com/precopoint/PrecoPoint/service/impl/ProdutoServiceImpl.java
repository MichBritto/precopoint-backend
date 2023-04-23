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
    public ResponseEntity<StatusResponseDto> updateProduto(UpdateProdutoRequestDto request) throws Exception {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            Produto produto = produtoRepository.findById(Integer.parseInt(request.getFindProdutoRequestDto().getProduto())).orElseThrow(
                    () -> new NotFoundException("Erro ao atualizar produto: produto '" + request.getProdutoRequestDto().getProduto() +"' não encontrado")
            );
            produto.setProduto(request.getProdutoRequestDto().getProduto());
            produto.setMarcaProduto(request.getProdutoRequestDto().getMarcaProduto());
            produto.setCategoria(categoriaRepository.findById(Integer.parseInt(request.getProdutoRequestDto().getCategoria())).orElseThrow(
                    () -> new NotFoundException("Erro ao atualizar produto: categoria '" + request.getProdutoRequestDto().getCategoria() +"' não encontrada")
            ));
            produto.setPreco(request.getProdutoRequestDto().getPreco());
            produto.setImagem(request.getProdutoRequestDto().getImagem());
            produto.setFornecedor(fornecedorRepository.findById(Integer.parseInt(request.getProdutoRequestDto().getFornecedor())).orElseThrow(
                    () -> new NotFoundException("Erro ao atualizar produto: fornecedor '" + request.getProdutoRequestDto().getFornecedor() +"' não encontrada")
            ));
            produtoRepository.save(produto);
            log.info("Produto Id: "+produto.getId() +" atualizado com sucesso");
            return ResponseEntity.ok(statusService.produtoAtualizadoStatusTrue());
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        catch(Exception e){
            throw new DefaultException("Erro ao atualizar dados de produto: "+ e.getMessage());
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
