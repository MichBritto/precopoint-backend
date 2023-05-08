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
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaProdutoDto {

    @NotBlank
    private String produtoId;
    @NotBlank
    private String listaId;
    @NotNull
    private int qtde;



    public ListaProduto toLista(ProdutoRepository produtoRepository, ListaRepository listaRepository){
        ListaProduto listaProduto = new ListaProduto();
        Lista lista = listaRepository.findById(Integer.parseInt(listaId)).orElseThrow(
                () -> new NotFoundException("Erro: lista não encontrada.")
        );
        Produto produtoAux = produtoRepository.findById(Integer.parseInt(produtoId)).orElseThrow(
                () -> new NotFoundException("Erro: produto não encontrado.")
        );
        listaProduto.setProduto(produtoAux);
        listaProduto.setLista(lista);
        listaProduto.setQtde(qtde);
        return listaProduto;
    }

}
