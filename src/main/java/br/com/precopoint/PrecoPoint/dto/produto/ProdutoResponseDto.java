package br.com.precopoint.PrecoPoint.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDto {
    private int id;
    private String produto;
    private double preco;
    private String imagem;
    private String descricao;
    private String marcaProduto;
    private String categoria;
    private String fornecedor;
}
