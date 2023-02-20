package br.com.precopoint.PrecoPoint.dto.lista;

import br.com.precopoint.PrecoPoint.model.Lista;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ListaRequestDto {
    @NotBlank
    private String id;


    public Lista toLista(){
        Lista lista = new Lista();
        lista.setId(Integer.parseInt(id));
        return lista;
    }
}
