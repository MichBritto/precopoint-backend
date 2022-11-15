package br.com.precopoint.PrecoPoint.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "consumidor")
public class Consumidor extends Usuario{


    public void criarLista(){
        ListaProduto listaProduto = new ListaProduto();

    }
    public void deletarLista(){

    }
    public void alterarLista(){

    }
    public void addProdutoLista(){

    }


}
