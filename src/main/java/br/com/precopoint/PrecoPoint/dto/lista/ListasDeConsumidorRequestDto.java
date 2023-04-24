package br.com.precopoint.PrecoPoint.dto.lista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListasDeConsumidorRequestDto {
    @NotBlank
    @Email
    private String email;
}
