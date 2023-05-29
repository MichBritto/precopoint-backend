package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
     Optional<Usuario> findByEmail(String email);
     Optional<Usuario> findByEmailAndStatusContaTrue(String email);
     Optional<Usuario> findByNome(String nome);
     List<Usuario> findByRolesNome(String nome);
}
