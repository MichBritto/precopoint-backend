package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumidorRepository extends JpaRepository<Consumidor, Integer> {

    List<Consumidor> findByEmail(String email);
}
