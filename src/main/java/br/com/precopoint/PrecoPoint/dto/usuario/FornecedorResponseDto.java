package br.com.precopoint.PrecoPoint.dto.usuario;


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
    private String endereco;
    private String logotipo;

}
