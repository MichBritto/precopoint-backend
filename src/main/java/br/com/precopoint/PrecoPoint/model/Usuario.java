package br.com.precopoint.PrecoPoint.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getEmail(), usuario.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                ", email='" + email + '\'' +
                ", tipoConta=" + tipoConta +
                '}';
    }
}
