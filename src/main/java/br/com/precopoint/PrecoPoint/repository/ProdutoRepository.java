package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Categoria;
import br.com.precopoint.PrecoPoint.model.Produto;
import br.com.precopoint.PrecoPoint.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Query("SELECT p FROM Produto p WHERE p.produto LIKE %:produto% GROUP BY p.produto, p.descricao, p.marcaProduto")
    List<Produto> findByProduto(String produto);
    @Query("SELECT p FROM Produto p WHERE p.produto = :produto AND  p.marcaProduto = :marcaProduto AND " +
            "p.fornecedor = :fornecedor")
    Optional <Produto> findProdutoByFornecedor(@Param("produto") String nomeProduto, @Param("marcaProduto") String marcaProduto, @Param("fornecedor") Usuario fornecedor);
    List<Produto> findAllByOrderByPrecoAsc();
    List<Produto> findAllByProdutoContainingIgnoreCaseOrderByPrecoAsc(String nome);
    @Query("SELECT p FROM Produto p WHERE p.preco <= :precoMax")
    List<Produto> findByPrecoMax(@Param("precoMax") double precoMax);
    @Query("SELECT p FROM Produto p WHERE p.preco >= :precoMin")
    List<Produto> findByPrecoMin(@Param("precoMin") double precoMin);
    @Query("SELECT p FROM Produto p WHERE p.preco >= :precoMin AND p.preco <= :precoMax")
    List<Produto> findByPrecoBetween( @Param("precoMin") double precoMin, @Param("precoMax") double precoMax);
    @Query("SELECT p FROM Produto p WHERE p.produto LIKE %:produto% AND p.preco >= :precoMin")
    List<Produto> findByProdutoAndPrecoMin(@Param("produto") String nome, @Param("precoMin") double precoMin);
    @Query("SELECT p FROM Produto p WHERE p.produto LIKE %:produto% AND p.preco <= :precoMax")
    List<Produto> findByProdutoAndPrecoMax(@Param("produto") String nome, @Param("precoMax") double precoMax);
    @Query("SELECT p FROM Produto p WHERE p.produto LIKE %:produto% AND p.preco >= :precoMin AND p.preco <= :precoMax")
    List<Produto> findByProdutoAndPrecoBetween(@Param("produto") String nome, @Param("precoMin") double precoMin, @Param("precoMax") double precoMax);
    List<Produto> findByCategoria(Categoria categoria);
    @Query("SELECT MIN(p) FROM Produto p GROUP BY p.produto, p.descricao, p.marcaProduto")
    List<Produto> findDistinctProdutoDescricaoMarca();

    @Query("SELECT p FROM Produto p WHERE p.id IN (SELECT MIN(p2.id) FROM Produto p2 WHERE p2.categoria = :categoria GROUP BY p2.produto, p2.descricao, p2.marcaProduto)")
    List<Produto> findByCategoriaDistinct(Categoria categoria);
}
