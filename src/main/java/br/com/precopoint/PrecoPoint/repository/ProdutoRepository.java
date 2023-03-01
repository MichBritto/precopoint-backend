package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Fornecedor;
import br.com.precopoint.PrecoPoint.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional <Produto> findByProduto(String produto);
    Optional <Produto> findByFornecedor(Fornecedor fornecedor);
    //Double sumPrecoByProdutoAndMarcaProdutoAndFornecedor(String produto, String marcaProduto, Fornecedor fornecedor);
    @Query("SELECT p FROM Produto p WHERE p.produto = :produto AND  p.marcaProduto = :marcaProduto AND " +
            "p.fornecedor = :fornecedor")
    Optional <Produto> findProdutoByFornecedor(@Param("produto") String nomeProduto, @Param("marcaProduto") String marcaProduto, @Param("fornecedor") Fornecedor fornecedor);

    List<Produto> findAllByOrderByPrecoAsc();
    List<Produto> findAllByProdutoOrderByPrecoAsc(String nome);
}
