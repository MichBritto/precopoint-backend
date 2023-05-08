package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByNome(String nome);
}
