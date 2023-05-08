package br.com.precopoint.PrecoPoint.dto.usuario.fornecedor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateFornecedorRequestDto {
    private String nome;
    private String cep;
    private String senha;
    private String logotipo;
}
