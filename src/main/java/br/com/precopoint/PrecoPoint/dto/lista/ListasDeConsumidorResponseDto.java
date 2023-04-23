package br.com.precopoint.PrecoPoint.dto.lista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListasDeConsumidorResponseDto {
    private int id;
    private String nomeLista;
    private int idConsumidor;
    private String consumidor;
}
