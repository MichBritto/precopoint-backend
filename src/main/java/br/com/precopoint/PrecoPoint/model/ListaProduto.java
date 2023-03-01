package br.com.precopoint.PrecoPoint.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


//@Table(name = "listaproduto")
@Data
@Entity
public class ListaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;


    @ManyToOne
    @JoinColumn(name = "lista_relacionada_id")
    private Lista listaRelacionada;

    private int qtde;

}
