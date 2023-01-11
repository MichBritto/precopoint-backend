package br.com.precopoint.PrecoPoint.dto.produto;

import br.com.precopoint.PrecoPoint.model.Fornecedor;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDto {




    @NotBlank
    private String produto;
    @DecimalMin(value = "0.1", inclusive = true)
    private double preco;
    @NotBlank
    private String imagem;
    @NotBlank
    private String descricao;
    @NotBlank
    private String marcaProduto;

    @NotBlank
    private String fornecedor;

    public Produto toProuduto(FornecedorRepository fornecedorRepository){
        Produto produtoAux = new Produto();
        Optional<Fornecedor> fornecedorAux =  fornecedorRepository.findByNome(fornecedor);

        produtoAux.setProduto(produto);
        produtoAux.setMarcaProduto(marcaProduto);
        produtoAux.setDescricao(descricao);
        produtoAux.setFornecedor(fornecedorAux.get());
        produtoAux.setImagem(imagem);
        produtoAux.setPreco(preco);

        return produtoAux;
    }
}
