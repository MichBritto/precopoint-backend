package br.com.precopoint.PrecoPoint.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String endereco;
    private String email;
    private String senha;

    //caso deseje guardar a string do enum
    //@Enumerated(EnumType.STRING)
    @Column(name = "tipoconta")
    private TipoConta tipoConta;
}
