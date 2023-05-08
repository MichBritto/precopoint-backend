package br.com.precopoint.PrecoPoint.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginFormDto {

    @NotBlank
    private String email;
    @NotBlank
    private String senha;

}
