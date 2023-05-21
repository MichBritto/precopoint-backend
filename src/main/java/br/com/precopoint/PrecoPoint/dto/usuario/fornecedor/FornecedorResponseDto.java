package br.com.precopoint.PrecoPoint.dto.usuario.fornecedor;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FornecedorResponseDto {
    private String id;
    private String nome;
    private String email;
    private String cep;
    private String logotipo;
    private Boolean statusConta;
}
