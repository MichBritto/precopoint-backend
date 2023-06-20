package br.com.precopoint.PrecoPoint.dto.usuario.fornecedor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateFornecedorRequestDto {
    @NotBlank
    @Email
    private String email;
    private String nome;
    private String cep;
    private String senha;
    private String logotipo;
    private Boolean statusConta;
}
