package br.com.precopoint.PrecoPoint.dto.lista;

import br.com.precopoint.PrecoPoint.model.ListaProduto;
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
        listaProduto.setProduto(produtoRepository.findById(Integer.parseInt(produto)).get());
        listaProduto.setListaRelacionada(listaRepository.findById(Integer.parseInt(listaRelacionada)).get());
        return listaProduto;
    }

}
