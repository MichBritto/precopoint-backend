package br.com.precopoint.PrecoPoint.dto.produto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindProdutoRequestDto {
    @NotBlank
    private String produto;

}
