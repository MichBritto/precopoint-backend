package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.dto.email.RecuperarSenhaRequestDto;
import br.com.precopoint.PrecoPoint.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping("/enviar-recuperacao-senha/{email}")
    public ResponseEntity<?> enviarEmailRecuperarSenha(@PathVariable("email") String email){
        return emailService.enviarEmailRecuperarSenha("email");
    }

    @PutMapping("redefinir-senha/{email}")
    public ResponseEntity<?> redefinirSenha(@PathVariable("email") String email,
                                            @RequestBody @Valid RecuperarSenhaRequestDto recuperarSenhaRequestDto){
        return emailService.recuperarSenha(email, recuperarSenhaRequestDto);
    }
}
