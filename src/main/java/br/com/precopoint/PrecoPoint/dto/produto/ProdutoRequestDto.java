package br.com.precopoint.PrecoPoint.dto.produto;

import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Categoria;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.model.Usuario;
import br.com.precopoint.PrecoPoint.repository.CategoriaRepository;
import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

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

    @NotBlank
    private String categoria;

    public Produto toProuduto(UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository){
        Produto produtoAux = new Produto();
        Usuario fornecedorAux =  usuarioRepository.findByEmail(fornecedor).orElseThrow(
                () -> new NotFoundException("Fornecedor '"+ fornecedor +"' não encontrado."));
        Categoria categoriaAux = categoriaRepository.findByCategoria(categoria).orElseThrow(
                () ->  new NotFoundException("Categoria '"+ categoria +"' não encontrada."));

        produtoAux.setProduto(produto);
        produtoAux.setMarcaProduto(marcaProduto);
        produtoAux.setDescricao(descricao);
        produtoAux.setFornecedor(fornecedorAux);
        produtoAux.setImagem(imagem);
        produtoAux.setPreco(preco);
        produtoAux.setCategoria(categoriaAux);

        return produtoAux;
    }
}
