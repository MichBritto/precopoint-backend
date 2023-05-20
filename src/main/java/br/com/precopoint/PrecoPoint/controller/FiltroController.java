package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.dto.filtro.CategoriaResponseDto;
import br.com.precopoint.PrecoPoint.dto.filtro.FornecedorFilterByDistanceResponseDto;
import br.com.precopoint.PrecoPoint.dto.produto.DistinctProdutoResponseDto;
import br.com.precopoint.PrecoPoint.dto.produto.FindProdutoRequestDto;
import br.com.precopoint.PrecoPoint.dto.produto.ProdutoResponseDto;
import br.com.precopoint.PrecoPoint.repository.CategoriaRepository;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import br.com.precopoint.PrecoPoint.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/filtro")
public class FiltroController {
    @Autowired
    FornecedorService fornecedorService;
    @Autowired
    ProdutoService produtoService;

    @Autowired
    CategoriaRepository categoriaRepository;
    @GetMapping("localizacao")
    ResponseEntity<List<FornecedorFilterByDistanceResponseDto>> getFornecedoresMaisProximos(
            @RequestParam("cep") String cep) throws Exception {
        return fornecedorService.getFornecedoresMaisProximos(cep);
    }

    @GetMapping("produto")
    ResponseEntity<?> getProdutosByFilter(@RequestParam(name = "produto", required = false) String produto,
                                          @RequestParam(name = "precoMin", required = false) Double precoMin,
                                          @RequestParam(name = "precoMax", required = false) Double precoMax){
        return produtoService.filterProdutos(produto,precoMin,precoMax);
    }

    @GetMapping("list-produto") //retorna todos os produtos sem ordenação
    ResponseEntity<List<ProdutoResponseDto>> getProduto(){
        return produtoService.getProduto();
    }

    @GetMapping("list-distinct-produto")//retorna apenas um produto com base em 'produto,descricao,marca_produto'
    ResponseEntity<List<DistinctProdutoResponseDto>>  getDistinctProduto() { return produtoService.getDistinctProduto(); }

    @GetMapping("list-produto-asc") //retorna os produtos do menor preço para o maior
    ResponseEntity<List<ProdutoResponseDto>> getProdutoAsc() throws Exception {
        return produtoService.getProdutoAsc();
    }

    @GetMapping("list-produto-nome")//retorna a lista dos produtos em ordem de preco de acordo com o nome do produto
    ResponseEntity<List<ProdutoResponseDto>> getProdutoByNomeAsc(@Valid @RequestBody FindProdutoRequestDto findProdutoRequestDto) throws Exception {
        return produtoService.getProdutoByNomeAsc(findProdutoRequestDto);
    }

    @GetMapping("get-categorias")
    ResponseEntity<List<CategoriaResponseDto>> getCategorias(){
        ModelMapper modelMapper = new ModelMapper();
        List<CategoriaResponseDto> categoriaResponseDto = categoriaRepository.findAll().stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaResponseDto.class)).toList();
        return ResponseEntity.ok(categoriaResponseDto);
    }

    @GetMapping("list-produtos-by-categoria/{id}")
    ResponseEntity<List<DistinctProdutoResponseDto>> getProdutosByCategoria(@PathVariable("id") int idCategoria){
        return produtoService.getProdutoByCategoria(idCategoria);
    }
}
