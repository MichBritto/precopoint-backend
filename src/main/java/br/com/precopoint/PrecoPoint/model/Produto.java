package br.com.precopoint.PrecoPoint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String produto;
    private double preco;
    private String imagem;
    private String descricao;
    private String marcaProduto;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Override
    public String toString() {
        return produto;
    }
}
