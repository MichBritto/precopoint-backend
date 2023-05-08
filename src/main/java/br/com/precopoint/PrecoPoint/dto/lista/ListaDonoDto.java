package br.com.precopoint.PrecoPoint.dto.lista;


import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Lista;
import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
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

    public Lista toLista(UsuarioRepository usuarioRepository){
        Lista lista = new Lista();
        lista.setNome(nomeLista);
        lista.setConsumidor(usuarioRepository.findById(consumidor).orElseThrow(
                () -> new NotFoundException("Erro: usuário não encontrado no sistema.")
        ));
        return lista;
    }
}
