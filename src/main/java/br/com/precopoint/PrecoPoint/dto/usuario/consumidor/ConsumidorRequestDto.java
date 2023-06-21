package br.com.precopoint.PrecoPoint.dto.usuario.consumidor;

import br.com.precopoint.PrecoPoint.model.Role;
import br.com.precopoint.PrecoPoint.model.Usuario;
import br.com.precopoint.PrecoPoint.repository.RoleRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import java.util.Set;

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

    public Usuario toConsumidor(RoleRepository roleRepository){
        Usuario consumidor = new Usuario();
        Role consumidorRole = roleRepository.findByNome("ROLE_CONSUMIDOR");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCrypt = passwordEncoder.encode(senha);
        consumidor.setNome(nome);
        consumidor.setCep(endereco);
        consumidor.setEmail(email);
        consumidor.setSenha(senhaCrypt);
        consumidor.setStatusConta(true);
        consumidor.setRoles(Set.of(consumidorRole));
        return consumidor;
    }


}
