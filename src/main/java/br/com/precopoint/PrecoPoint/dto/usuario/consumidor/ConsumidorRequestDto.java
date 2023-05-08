package br.com.precopoint.PrecoPoint.dto.usuario.consumidor;

import br.com.precopoint.PrecoPoint.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ConsumidorRequestDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;

    public Usuario toConsumidor(){
        Usuario consumidor = new Usuario();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCrypt = passwordEncoder.encode(senha);
        consumidor.setNome(nome);
        consumidor.setCep(endereco);
        consumidor.setEmail(email);
        consumidor.setSenha(senhaCrypt);
        return consumidor;
    }


}
