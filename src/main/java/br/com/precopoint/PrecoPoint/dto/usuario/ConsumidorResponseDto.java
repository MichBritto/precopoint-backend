package br.com.precopoint.PrecoPoint.dto.usuario;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ConsumidorResponseDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String email;

}
