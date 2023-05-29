package br.com.precopoint.PrecoPoint.config.security;

import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class VerifyUserStatus {

    @Autowired
    UsuarioRepository usuarioRepository;

    public void authenticationWithStatusUserTrue(String email) {
        try{
            usuarioRepository.findByEmailAndStatusContaTrue(email).orElseThrow(
                    () -> new DefaultException("Erro, usuário não está ativo ou registrado no sistema.")
            );
        } catch (Exception e){
            throw new DefaultException(e.getMessage());
        }
    }
}
