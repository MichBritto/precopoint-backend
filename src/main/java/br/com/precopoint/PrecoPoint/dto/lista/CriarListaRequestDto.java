package br.com.precopoint.PrecoPoint.dto.lista;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarListaRequestDto {
    @NotBlank
    private String nomeLista;
    @NotBlank
    @Email
    private String emailConsumidor;
}
