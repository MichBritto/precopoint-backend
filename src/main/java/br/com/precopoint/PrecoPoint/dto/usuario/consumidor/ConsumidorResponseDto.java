package br.com.precopoint.PrecoPoint.dto.usuario.consumidor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumidorResponseDto {
    private int id;
    private String nome;
    private String email;
    private String cep;
}
