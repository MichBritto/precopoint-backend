package br.com.precopoint.PrecoPoint.config.security;

import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Role;
import br.com.precopoint.PrecoPoint.model.Usuario;
import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRolesByUser {
    @Autowired
    UsuarioRepository usuarioRepository;
    public List<String> rolesStringByUser(String user) {
        Usuario usuario = usuarioRepository.findByEmail(user).orElseThrow(
                () -> new NotFoundException("usuario '" + user + "' não encontrado."));
        return usuario.getRoles().stream().map(Role::getNome).toList();
    }
}
