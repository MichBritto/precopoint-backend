package br.com.precopoint.PrecoPoint.dto.usuario;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatusResponseDto {

    private String mensagem;
    private boolean status;
}
