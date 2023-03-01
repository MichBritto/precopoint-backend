package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.lista.ListaDonoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaRequestDto;
import br.com.precopoint.PrecoPoint.dto.lista.ValorTotalResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.model.Consumidor;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.repository.*;
import br.com.precopoint.PrecoPoint.service.ListaService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ListaServiceImpl implements ListaService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    ListaRepository listaRepository;
    @Autowired
    ListaProdutoRepository listaProdutoRepository;
    @Autowired
    ConsumidorRepository consumidorRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    FornecedorRepository fornecedorRepository;
    @Autowired
    StatusService statusService;
    @Autowired
    AuthenticationController authenticationController;
    @Override
    public ResponseEntity<StatusResponseDto> criarLsita(ListaDonoDto request) {
        try{
            listaRepository.save(request.toLista(consumidorRepository));
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.listaStatusTrue().getMensagem());
            return ResponseEntity.ok(statusService.listaStatusTrue());
        }catch(Exception e){
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.listaStatusFalse().getMensagem());
        }
        ThreadContext.put("user", authenticationController.getUser());
        logger.info(statusService.listaStatusFalse().getMensagem());
        return ResponseEntity.badRequest().body(statusService.listaStatusFalse());
    }

    @Override
    public ResponseEntity<StatusResponseDto> addProduto(ListaProdutoDto request) {
        try{
            listaProdutoRepository.save(request.toLista(produtoRepository,listaRepository));
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.addProdutoListaTrue().getMensagem());
            return ResponseEntity.ok(statusService.addProdutoListaTrue());
        }catch(Exception e){
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.addProdutoListaFalse().getMensagem());
        }
        ThreadContext.put("user", authenticationController.getUser());
        logger.info(statusService.addProdutoListaFalse().getMensagem());
        return ResponseEntity.badRequest().body(statusService.addProdutoListaFalse());
    }

    @Override
    public ResponseEntity<List<?>> getListaConsumidor(ConsumidorResponseDto consumidor) throws Exception {

        try{
            Optional<Consumidor> response = consumidorRepository.findByEmail(consumidor.getEmail());
            return ResponseEntity.ok(listaRepository.findAllByConsumidor(response.get()));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<?>> getProdutosByLista(ListaRequestDto listaRequestDto) throws Exception {
        try{
            return ResponseEntity.ok(listaProdutoRepository.findAllByLista(listaRequestDto.toLista()));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getValorLista(ListaRequestDto listaRequestDto) throws Exception {
        try{
            List<Produto> produtos = listaProdutoRepository.findAllByLista(listaRequestDto.toLista());
            ValorTotalResponseDto response = new ValorTotalResponseDto();
            Map<String, List<String>> produtosNaoEncontrados = new HashMap<>();
            for(Produto produto : produtos){
                Optional<Produto> carrefourProduto = produtoRepository.findProdutoByFornecedor(produto.getProduto(),produto.getMarcaProduto(), fornecedorRepository.findById(1).get());
                Optional<Produto> extraProduto = produtoRepository.findProdutoByFornecedor(produto.getProduto(),produto.getMarcaProduto(), fornecedorRepository.findById(2).get());
                Optional<Produto> semarProduto = produtoRepository.findProdutoByFornecedor(produto.getProduto(),produto.getMarcaProduto(), fornecedorRepository.findById(3).get());
                Optional<Produto> lourenciniProduto = produtoRepository.findProdutoByFornecedor(produto.getProduto(),produto.getMarcaProduto(), fornecedorRepository.findById(4).get());
                Optional<Produto> atacadaoProduto = produtoRepository.findProdutoByFornecedor(produto.getProduto(),produto.getMarcaProduto(), fornecedorRepository.findById(5).get());
                //Carrefour
                if (carrefourProduto.isPresent()) {
                    response.setCarrefour(response.getCarrefour() + carrefourProduto.get().getPreco());
                } else {
                    if (!produtosNaoEncontrados.containsKey("carrefour")) {
                        produtosNaoEncontrados.put("carrefour", new ArrayList<>()); // create a new list for products not found in Carrefour
                    }
                    produtosNaoEncontrados.get("carrefour").add(produto.getProduto()); // add the product to the list of products not found in Carrefour
                }
                //Extra
                if (extraProduto.isPresent()) {
                    response.setExtra(response.getExtra() + extraProduto.get().getPreco());
                } else {
                    if (!produtosNaoEncontrados.containsKey("extra")) {
                        produtosNaoEncontrados.put("extra", new ArrayList<>());
                    }
                    produtosNaoEncontrados.get("extra").add(produto.getProduto());
                }
                //Semar
                if (semarProduto.isPresent()) {
                    response.setSemar(response.getSemar() + semarProduto.get().getPreco());
                } else {
                    if (!produtosNaoEncontrados.containsKey("semar")) {
                        produtosNaoEncontrados.put("semar", new ArrayList<>());
                    }
                    produtosNaoEncontrados.get("semar").add(produto.getProduto());
                }
                //Lourencini
                if (lourenciniProduto.isPresent()) {
                    response.setLourencini(response.getLourencini() + lourenciniProduto.get().getPreco());
                } else {
                    if (!produtosNaoEncontrados.containsKey("lourencini")) {
                        produtosNaoEncontrados.put("lourencini", new ArrayList<>());
                    }
                    produtosNaoEncontrados.get("lourencini").add(produto.getProduto());
                }
                //Atacadao
                if (atacadaoProduto.isPresent()) {
                    response.setAtacadao(response.getAtacadao() + atacadaoProduto.get().getPreco());
                } else {
                    if (!produtosNaoEncontrados.containsKey("atacadão")) {
                        produtosNaoEncontrados.put("atacadão", new ArrayList<>());
                    }
                    produtosNaoEncontrados.get("atacadão").add(produto.getProduto());
                }
            }
            Map<String, Object> result = new HashMap<>(); // create a map to hold the response values
            result.put("fornecedores", response); // add the values for the supermarkets that did have the products
            result.put("produtos-nao-encontrados", produtosNaoEncontrados); // add the map of products not found by supermarket to the response
            return ResponseEntity.ok(result); // return the map as the JSON response
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
