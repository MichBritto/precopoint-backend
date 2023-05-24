package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Lista;
import br.com.precopoint.PrecoPoint.model.ListaProduto;
import br.com.precopoint.PrecoPoint.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListaProdutoRepository extends JpaRepository<ListaProduto, Integer> {
    @Query("SELECT lp.produto, lp.qtde FROM ListaProduto lp WHERE lp.lista = :lista")
    List<Object[]> findAllByLista(@Param("lista") Lista lista);

    @Query("SELECT lp FROM ListaProduto lp WHERE lp.lista = :lista")
    List<ListaProduto> findAllByListaDefault(@Param("lista") Lista lista);

    Optional<ListaProduto> findFirstByProdutoAndLista(Produto produto, Lista lista);
}
