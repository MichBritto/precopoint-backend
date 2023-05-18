package br.com.precopoint.PrecoPoint.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
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

    public Lista(String nomeLista, Usuario consumidor){
        this.nome = nomeLista;
        this.consumidor = consumidor;
    }
}
