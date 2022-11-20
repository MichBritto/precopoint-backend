package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumidorRepository extends JpaRepository<Consumidor, Integer> {

     Optional<Consumidor> findByEmail(String email);
}
