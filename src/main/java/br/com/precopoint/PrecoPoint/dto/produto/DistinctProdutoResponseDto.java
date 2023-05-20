package br.com.precopoint.PrecoPoint.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistinctProdutoResponseDto {
    private int id;
    private String produto;
    private String descricao;
    private String marcaProduto;
    private String imagem;
    private String categoria;
}
