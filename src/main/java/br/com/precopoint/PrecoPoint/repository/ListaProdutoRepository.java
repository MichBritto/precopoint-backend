package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.ListaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaProdutoRepository extends JpaRepository<ListaProduto, Integer> {
}
