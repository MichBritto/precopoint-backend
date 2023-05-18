package br.com.precopoint.PrecoPoint.dto.usuario.consumidor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateConsumidorRequestDto {
    @NotBlank
    @Email
    private String email;
    private String nome;
    private String endereco;
    private String senha;
}
