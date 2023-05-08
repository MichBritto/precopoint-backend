package br.com.precopoint.PrecoPoint.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lista")
public class Lista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "consumidor_id")
    private Usuario consumidor;
}
