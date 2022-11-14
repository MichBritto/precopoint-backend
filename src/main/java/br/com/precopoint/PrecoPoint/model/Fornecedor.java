package br.com.precopoint.PrecoPoint.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fornecedor")
public class Fornecedor extends Usuario{

    private String cnpj;
    private String logotipo;
}
