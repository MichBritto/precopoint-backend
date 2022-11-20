package br.com.precopoint.PrecoPoint.dto.usuario;

import br.com.precopoint.PrecoPoint.model.Consumidor;
import br.com.precopoint.PrecoPoint.model.TipoConta;
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

    public Consumidor toConsumidor(){
        Consumidor consumidor = new Consumidor();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCrypt = passwordEncoder.encode(senha);
        consumidor.setNome(nome);
        consumidor.setEndereco(endereco);
        consumidor.setEmail(email);
        consumidor.setSenha(senhaCrypt);
        consumidor.setTipoConta(TipoConta.CONSUMIDOR);
        return consumidor;
    }


}
