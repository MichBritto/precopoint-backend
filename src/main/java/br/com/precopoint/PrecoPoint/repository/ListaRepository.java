package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Consumidor;
import br.com.precopoint.PrecoPoint.model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ListaRepository extends JpaRepository<Lista, Integer> {

    @Query("select e from Lista e where e.consumidor = :consumidor")
    List<Lista> findAllByConsumidor(@Param("consumidor") Consumidor consumidor);

}
