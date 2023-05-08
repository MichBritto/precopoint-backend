package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.lista.*;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.model.Usuario;
import br.com.precopoint.PrecoPoint.repository.ListaProdutoRepository;
import br.com.precopoint.PrecoPoint.repository.ListaRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
import br.com.precopoint.PrecoPoint.service.ListaService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ListaServiceImpl implements ListaService {
    @Autowired
    ListaRepository listaRepository;
    @Autowired
    ListaProdutoRepository listaProdutoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    StatusService statusService;
    @Autowired
    AuthenticationController authenticationController;
    @Override
    public ResponseEntity<StatusResponseDto> criarLsita(ListaDonoDto request) {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            listaRepository.save(request.toLista(usuarioRepository));
            log.info(statusService.listaStatusTrue().getMensagem());
            return ResponseEntity.status(HttpStatus.CREATED).body(statusService.listaStatusTrue());
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException("Erro ao criar lista: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<StatusResponseDto> addProduto(ListaProdutoDto request) {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            listaProdutoRepository.save(request.toLista(produtoRepository,listaRepository));
            log.info(statusService.addProdutoListaTrue().getMensagem());
            return ResponseEntity.status(HttpStatus.CREATED).body(statusService.addProdutoListaTrue());
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException("Erro ao adicionar produto a lista: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<?>> getListaConsumidor(ListasDeConsumidorRequestDto consumidor) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            Usuario consumidorAux = usuarioRepository.findByEmail(consumidor.getEmail()).orElseThrow(
                    () -> new NotFoundException("Consumidor '"+ consumidor.getEmail() +"' não encontrado no sistema."));
            List<ListasDeConsumidorResponseDto> list = listaRepository.findAllByConsumidor(consumidorAux).stream()
                    .map(lista -> {
                        ListasDeConsumidorResponseDto response = modelMapper.map(lista, ListasDeConsumidorResponseDto.class);
                        response.setConsumidor(lista.getConsumidor().getEmail());
                        response.setIdConsumidor(lista.getConsumidor().getId());
                        return response;
                    }).toList();
            return ResponseEntity.ok(list);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new DefaultException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<?>> getProdutosByLista(ListaRequestDto listaRequestDto) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            List<Object[]> listaAux = listaProdutoRepository.findAllByLista(listaRequestDto.toLista());
            List<ProdutoResponseDto> list = listaAux.stream()
                    .map(objects -> {
                        Produto produto = (Produto) objects[0];
                        ProdutoResponseDto produtoResponseDto = modelMapper.map(produto, ProdutoResponseDto.class);
                        produtoResponseDto.setFornecedor(produto.getFornecedor().getNome());
                        produtoResponseDto.setCategoria(produto.getCategoria().getCategoria());
                        return produtoResponseDto;
                    }).toList();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            throw new DefaultException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getValorLista(ListaRequestDto listaRequestDto) throws Exception {
        try {
            List<Object[]> produtosAndQtde = listaProdutoRepository.findAllByLista(listaRequestDto.toLista());
            ValorTotalResponseDto response = new ValorTotalResponseDto();
            Map<String, List<String>> produtosNaoEncontrados = new HashMap<>();
            List<Usuario> fornecedores = usuarioRepository.findAll().stream()
                    .filter(usuario -> usuario.getRoles().stream()
                            .anyMatch(role -> role.getNome().equals("ROLE_FORNECEDOR"))).toList();

            for (Object[] result : produtosAndQtde) {
                Produto produto = (Produto) result[0];
                int qtde = (int) result[1];

                for (Usuario fornecedor : fornecedores) {
                    Optional<Produto> produtoFornecedor = produtoRepository.findProdutoByFornecedor(produto.getProduto(), produto.getMarcaProduto(), fornecedor);
                    String fornecedorName = fornecedor.getNome();

                    produtoFornecedor.ifPresentOrElse(
                            produtoFornecedorAux -> {
                                switch (fornecedorName.toLowerCase()) {
                                    case "carrefour" ->
                                            response.setCarrefour(response.getCarrefour() + (produtoFornecedor.get().getPreco() * qtde));
                                    case "extra" ->
                                            response.setExtra(response.getExtra() + (produtoFornecedor.get().getPreco() * qtde));
                                    case "semar" ->
                                            response.setSemar(response.getSemar() + (produtoFornecedor.get().getPreco() * qtde));
                                    case "lourencini" ->
                                            response.setLourencini(response.getLourencini() + (produtoFornecedor.get().getPreco() * qtde));
                                    case "atacadao" ->
                                            response.setAtacadao(response.getAtacadao() + (produtoFornecedor.get().getPreco() * qtde));
                                    default -> {
                                    }
                                }
                            },
                            () -> produtosNaoEncontrados.computeIfAbsent(fornecedorName.toLowerCase(), k -> new ArrayList<>()).add(produto.getProduto()+ " - " +produto.getMarcaProduto())
                    );
                }
            }
            Map<String, Object> result = new HashMap<>();
            result.put("fornecedores", response);
            result.put("produtos-nao-encontrados", produtosNaoEncontrados);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            throw new DefaultException("Erro ao pegar valor total de lista por fornecedor: "+e.getMessage());
        }
    }
}
