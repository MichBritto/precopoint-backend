package br.com.precopoint.PrecoPoint.dto.usuario.fornecedor;

import br.com.precopoint.PrecoPoint.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class FornecedorRequestDto{

    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    private String cnpj;
    private String logotipo;

    public Usuario toFornecedor(){
        Usuario fornecedor = new Usuario();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCrypt = passwordEncoder.encode(senha);
        fornecedor.setNome(nome);
        fornecedor.setCep(endereco);
        fornecedor.setEmail(email);
        fornecedor.setSenha(senhaCrypt);
        fornecedor.setCnpj(cnpj);
        fornecedor.setLogotipo(logotipo);
        return fornecedor;
    }


}
