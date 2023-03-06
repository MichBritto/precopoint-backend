package br.com.precopoint.PrecoPoint.dto.produto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProdutoRequestDto  {
    @NotNull
    private FindProdutoRequestDto findProdutoRequestDto;
    @NotNull
    private ProdutoRequestDto    produtoRequestDto;
}
