package br.com.precopoint.PrecoPoint.dto.lista;


import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Lista;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaDonoDto {

    @NotBlank
    private String nomeLista;

    @NotNull
    private int consumidor;

    public Lista toLista(ConsumidorRepository consumidorRepository){
        Lista lista = new Lista();
        lista.setNomeLista(nomeLista);
        lista.setConsumidor(consumidorRepository.findById(consumidor).orElseThrow(
                () -> new NotFoundException("Erro: usuário não encontrado no sistema.")
        ));
        return lista;
    }
}
