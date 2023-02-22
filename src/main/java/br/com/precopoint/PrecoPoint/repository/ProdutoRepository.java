package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Fornecedor;
import br.com.precopoint.PrecoPoint.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional <Produto> findByProduto(String produto);
    Optional <Produto> findByFornecedor(Fornecedor fornecedor);

    List<Produto> findAllByOrderByPrecoAsc();
    List<Produto> findAllByProdutoOrderByPrecoAsc(String nome);
}
