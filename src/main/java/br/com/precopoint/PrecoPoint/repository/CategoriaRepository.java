package br.com.precopoint.PrecoPoint.repository;


import br.com.precopoint.PrecoPoint.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

    Optional<Categoria> findByCategoria(String nome);
}
