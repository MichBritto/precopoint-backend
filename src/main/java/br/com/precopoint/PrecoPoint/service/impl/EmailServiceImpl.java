package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.dto.email.RecuperarSenhaRequestDto;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Usuario;
import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
import br.com.precopoint.PrecoPoint.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<?> enviarEmailRecuperarSenha(String email) {
        try{
            String emailEncoded = email.replace("@", "%40");
            String resetPasswordLink = "http://localhost:8081/login/redefinir-senha";
            String emailBody = "Olá, você solicitou a redefinição de senha para a sua conta. Para redefinir sua senha, clique no seguinte link: " + resetPasswordLink;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("precopoint@gmail.com");
            message.setTo("michel.brito.9699@gmail.com");
            message.setSubject("Preço Point: Redefinição de senha");
            message.setText(emailBody);
            javaMailSender.send(message);
            log.info("E-mail de redefinição de senha enviado com sucesso");
            return ResponseEntity.ok().build();
        } catch(Exception e){
            throw new DefaultException("Erro ao enviar e-mail de recuperção de senha: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> recuperarSenha(String email, RecuperarSenhaRequestDto recuperarSenhaRequestDto) {
        try{
            Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                    () ->  new NotFoundException("Erro: usuário com email '"+ email +"' não encontrado"));
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String senhaCrypt = passwordEncoder.encode(recuperarSenhaRequestDto.getNovaSenha());
            usuario.setSenha(senhaCrypt);
            usuarioRepository.save(usuario);
            log.info("Senha de usuário '"+ email +"' atualizada com sucesso.");
            return ResponseEntity.ok().build();
        } catch (Exception e){
            throw new DefaultException("Erro ao redefinir senha: "+ e.getMessage());
        }
    }
}
