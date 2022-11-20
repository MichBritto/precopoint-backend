package br.com.precopoint.PrecoPoint.dto.usuario;

import br.com.precopoint.PrecoPoint.model.Fornecedor;
import br.com.precopoint.PrecoPoint.model.TipoConta;
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

    public Fornecedor toFornecedor(){
        Fornecedor fornecedor = new Fornecedor();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCrypt = passwordEncoder.encode(senha);
        fornecedor.setNome(nome);
        fornecedor.setEndereco(endereco);
        fornecedor.setEmail(email);
        fornecedor.setSenha(senhaCrypt);
        fornecedor.setCnpj(cnpj);
        fornecedor.setLogotipo(logotipo);
        fornecedor.setTipoConta(TipoConta.FORNECEDOR);
        return fornecedor;
    }


}
