package br.com.precopoint.PrecoPoint.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomeLista;

    @ManyToOne
    private Consumidor consumidor;

}
