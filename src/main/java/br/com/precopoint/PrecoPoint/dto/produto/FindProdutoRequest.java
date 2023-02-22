package br.com.precopoint.PrecoPoint.dto.produto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindProdutoRequest {

    @NotBlank
    private String produto;

}
