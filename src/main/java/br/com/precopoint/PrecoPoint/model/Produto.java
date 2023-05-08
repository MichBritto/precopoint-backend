package br.com.precopoint.PrecoPoint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String produto;
    @Column(nullable = false)
    private double preco;
    @Column(nullable = false)
    private String imagem;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String marcaProduto;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "fornecedor_id", referencedColumnName = "id")
    private Usuario fornecedor;

    @Override
    public String toString() {
        return produto;
    }
}
