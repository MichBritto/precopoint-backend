package br.com.precopoint.PrecoPoint.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Lista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomeLista;
    @ManyToOne
    private Consumidor consumidor;
    private double valorTotal = 0;
}
