package br.com.precopoint.PrecoPoint.model;

import lombok.Data;

import javax.persistence.*;


//@Table(name = "listaproduto")
@Data
@Entity
@Table(name = "lista_produto")
public class ListaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;


    @ManyToOne
    @JoinColumn(name = "lista_id")
    private Lista lista;

    private int qtde;

}
