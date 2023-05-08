package br.com.precopoint.PrecoPoint.dto.filtro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FornecedorFilterByDistanceResponseDto {
    private String id;
    private String nome;
    private String email;
    private String endereco;
    private String logotipo;
    private double distancia;
}
