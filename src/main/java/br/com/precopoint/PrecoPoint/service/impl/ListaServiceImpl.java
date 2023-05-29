package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.lista.CriarListaRequestDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaValorTotalResponseDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListasDeConsumidorResponseDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.exception.AlreadyExistsException;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Lista;
import br.com.precopoint.PrecoPoint.model.ListaProduto;
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
    public ResponseEntity<StatusResponseDto> criarLsita(CriarListaRequestDto request) {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            Usuario consumidor = usuarioRepository.findByEmail(request.getEmailConsumidor()).orElseThrow(
                    () -> new NotFoundException("Usuário '"+ request.getEmailConsumidor() + "' não encontrado."));
            listaRepository.findByNomeAndConsumidor(request.getNomeLista(),consumidor).ifPresent(
                    (lista) -> {
                        throw new AlreadyExistsException("Outra lista já foi cadastrada com este mesmo nome pelo usuário '"+
                                                            request.getEmailConsumidor() +"'");
                    });
            if(listaRepository.findAllByConsumidor(consumidor).size() >= 7) {
                throw new AlreadyExistsException("Consumidor atingiu o limite de listas, para criar uma nova, exclua uma não utilizada.");
            }
            listaRepository.save(new Lista(request.getNomeLista(), consumidor));
            log.info(statusService.listaStatusTrue().getMensagem());
            return ResponseEntity.status(HttpStatus.CREATED).body(statusService.listaStatusTrue());
        }catch(AlreadyExistsException e){
            throw new AlreadyExistsException("Erro ao criar lista: "+ e.getMessage());
        }catch(NotFoundException e){
            throw new NotFoundException("Erro ao criar lista: "+ e.getMessage());
        }catch(Exception e){
            throw new DefaultException("Erro ao criar lista: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> addProduto(ListaProdutoDto request) {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            Lista lista = listaRepository.findById(Integer.parseInt(request.getListaId())).orElseThrow(
                    () -> new NotFoundException("Erro: lista não encontrada.")
            );
            Produto produto = produtoRepository.findById(Integer.parseInt(request.getProdutoId())).orElseThrow(
                    () -> new NotFoundException("Erro: produto não encontrado.")
            );
            Optional<ListaProduto> listaProduto = listaProdutoRepository.findFirstByProdutoAndLista(produto, lista);
            if (listaProduto.isPresent()) {
                ListaProduto itemEncontrado = listaProduto.get();
                int qtdeAnterior = itemEncontrado.getQtde();
                itemEncontrado.setQtde(itemEncontrado.getQtde() + request.getQtde());
                if (qtdeAnterior > itemEncontrado.getQtde()){
                    if (itemEncontrado.getQtde() <= 0) {
                        listaProdutoRepository.delete(itemEncontrado);
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                    }
                    listaProdutoRepository.save(itemEncontrado);
                    return ResponseEntity.ok().build();
                }
                listaProdutoRepository.save(itemEncontrado);
            } else {
                ListaProduto newLista = new ListaProduto();
                newLista.setProduto(produto);
                newLista.setLista(lista);
                newLista.setQtde(request.getQtde());
                listaProdutoRepository.save(newLista);
            }
            log.info(statusService.addProdutoListaTrue().getMensagem());
            return ResponseEntity.status(HttpStatus.CREATED).body(statusService.addProdutoListaTrue());
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException("Erro ao adicionar produto a lista: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<?>> getListaConsumidor(String email) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            Usuario consumidorAux = usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("Consumidor '"+ email +"' não encontrado no sistema."));
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
    public ResponseEntity<List<?>> getProdutosByLista(int idLista) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            Lista lista = listaRepository.findById(idLista).orElseThrow(
                    () -> new NotFoundException("Lista de id '"+ idLista +"' não encontrada.")
            );
            List<Object[]> listaAux = listaProdutoRepository.findAllByLista(lista);
            List<ProdutoResponseDto> list = listaAux.stream()
                    .map(objects -> {
                        Produto produto = (Produto) objects[0];
                        int qtde = (int) objects[1] ;
                        ProdutoResponseDto produtoResponseDto = modelMapper.map(produto, ProdutoResponseDto.class);
                        produtoResponseDto.setFornecedor(produto.getFornecedor().getNome());
                        produtoResponseDto.setCategoria(produto.getCategoria().getCategoria());
                        produtoResponseDto.setQtde(qtde);
                        return produtoResponseDto;
                    }).toList();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            throw new DefaultException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getValorLista(int idLista) throws Exception {
        try {
            Lista lista = listaRepository.findById(idLista).orElseThrow(
                    () -> new NotFoundException("Lista de id '"+ idLista +"' não encontrada.")
            );
            List<Object[]> produtosAndQtde = listaProdutoRepository.findAllByLista(lista);
            Map<String, List<String>> produtosNaoEncontrados = new HashMap<>();
            Map<String, ListaValorTotalResponseDto> valorTotal = new HashMap<>();

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
                                ListaValorTotalResponseDto listaValorTotal = valorTotal.getOrDefault(fornecedorName.toLowerCase(), new ListaValorTotalResponseDto());
                                listaValorTotal.setValorTotal(listaValorTotal.getValorTotal() + (produtoFornecedorAux.getPreco() * qtde));
                                listaValorTotal.setLogotipo(fornecedor.getLogotipo());
                                valorTotal.put(fornecedorName.toLowerCase(), listaValorTotal);
                            },
                            () -> produtosNaoEncontrados.computeIfAbsent(fornecedorName.toLowerCase(), k -> new ArrayList<>()).add(produto.getProduto() + " - " + produto.getMarcaProduto())
                    );
                }
            }
            Map<String, Object> result = new HashMap<>();
            result.put("fornecedores", valorTotal);
            result.put("produtos-nao-encontrados", produtosNaoEncontrados);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new DefaultException("Erro ao pegar valor total de lista por fornecedor: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getListaByFornecedor(int idLista, String nomeFornecedor) {
        ModelMapper modelMapper = new ModelMapper();
        Lista lista = listaRepository.findById(idLista).orElseThrow(
                () -> new NotFoundException("Lista de id '"+ idLista +"' não encontrada."));
        Usuario fornecedor = usuarioRepository.findByNome(nomeFornecedor).orElseThrow(
                () -> new NotFoundException("Usuario '"+ nomeFornecedor +"' não encontrado."));
        List<ListaProduto> listaProduto = listaProdutoRepository.findAllByListaDefault(lista);
        List<ProdutoResponseDto> listByFornecedor = listaProduto.stream()
            .map(listaProduto1 -> {
                Optional<Produto> optionalProduto = produtoRepository.findByProdutoAndMarcaProdutoAndFornecedor(
                        listaProduto1.getProduto().getProduto(),
                        listaProduto1.getProduto().getMarcaProduto(),
                        fornecedor
                );
                return optionalProduto.map(produto -> {
                            var finalProduto = modelMapper.map(produto, ProdutoResponseDto.class);
                            finalProduto.setFornecedor(produto.getFornecedor().getNome());
                            finalProduto.setQtde(listaProduto1.getQtde());
                            return finalProduto;
                        })
                        .orElse(null);
            })
            .filter(Objects::nonNull)
            .toList();
        
        return ResponseEntity.ok(listByFornecedor);
    }

    @Override
    public ResponseEntity<?> deleteLista(int idLista) {
        try{
            listaRepository.findById(idLista).ifPresentOrElse(
                    lista ->  {
                        List<ListaProduto> listaProduto = listaProdutoRepository.findByLista(lista);
                        listaProdutoRepository.deleteAll(listaProduto);
                        listaRepository.delete(lista);
                    },
                    () -> {
                        throw new NotFoundException("Erro, lista com id '"+ idLista +"' não encontrada.");
                    }
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        } catch(Exception e){
            throw new DefaultException(e.getMessage());
        }
    }

}
