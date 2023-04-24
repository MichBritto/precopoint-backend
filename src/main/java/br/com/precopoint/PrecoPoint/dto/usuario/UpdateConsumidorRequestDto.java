package br.com.precopoint.PrecoPoint.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateConsumidorRequestDto {
    private String nome;
    private String endereco;
    private String senha;
}
