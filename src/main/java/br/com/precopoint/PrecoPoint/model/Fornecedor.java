package br.com.precopoint.PrecoPoint.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fornecedor")
public class Fornecedor extends Usuario{

    @NotBlank
    private String cnpj;
    @NotBlank
    private String logotipo;


}
