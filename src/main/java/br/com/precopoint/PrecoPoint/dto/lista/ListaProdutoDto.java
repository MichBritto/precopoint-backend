package br.com.precopoint.PrecoPoint.dto.lista;

import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Lista;
import br.com.precopoint.PrecoPoint.model.ListaProduto;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.repository.ListaRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaProdutoDto {

    @NotBlank
    private String produto;


    @NotBlank
    private String listaRelacionada;


    public ListaProduto toLista(ProdutoRepository produtoRepository, ListaRepository listaRepository){
        ListaProduto listaProduto = new ListaProduto();
        Lista lista = listaRepository.findById(Integer.parseInt(listaRelacionada)).orElseThrow(
                () -> new NotFoundException("Erro: lista não encontrada.")
        );
        Produto produtoAux = produtoRepository.findById(Integer.parseInt(produto)).orElseThrow(
                () -> new NotFoundException("Erro: produto não encontrado.")
        );
        listaProduto.setProduto(produtoAux);
        listaProduto.setListaRelacionada(lista);
        lista.setValorTotal(lista.getValorTotal() + produtoAux.getPreco());
        return listaProduto;
    }

}
