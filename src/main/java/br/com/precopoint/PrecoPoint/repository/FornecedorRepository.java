package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Optional<Fornecedor> findByEmail(String email);
    Optional<Fornecedor> findByNome(String nome);

}
