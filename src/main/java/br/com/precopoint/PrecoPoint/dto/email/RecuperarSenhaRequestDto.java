package br.com.precopoint.PrecoPoint.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecuperarSenhaRequestDto {
    @NotBlank
    private String novaSenha;
}
