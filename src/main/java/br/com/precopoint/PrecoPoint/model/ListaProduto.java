package br.com.precopoint.PrecoPoint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity

public class ListaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Consumidor consumidor;

    //private List<Produto> produtos;

}
