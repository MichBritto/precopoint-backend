package br.com.precopoint.PrecoPoint.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateProdutoRequestDto {
    private String produto;
    private double preco;
    private String imagem;
    private String descricao;
    private String marcaProduto;
    private String fornecedor;
    private String categoria;
}
