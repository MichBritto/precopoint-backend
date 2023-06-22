package br.com.precopoint.PrecoPoint.service;

import br.com.precopoint.PrecoPoint.dto.email.RecuperarSenhaRequestDto;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<?> enviarEmailRecuperarSenha(String email);
    ResponseEntity<?> recuperarSenha(String email, RecuperarSenhaRequestDto recuperarSenhaRequestDto);
}
