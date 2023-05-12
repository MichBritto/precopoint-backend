package br.com.precopoint.PrecoPoint.dto.lista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaValorTotalResponseDto {
    private double valorTotal=0;
    private String logotipo;
}
