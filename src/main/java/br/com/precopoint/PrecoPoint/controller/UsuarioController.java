package br.com.precopoint.PrecoPoint.controller;


import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.UsuarioResponseDto;
import br.com.precopoint.PrecoPoint.model.Consumidor;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("usuario")
public class UsuarioController {


    @Autowired
    ConsumidorRepository consumidorRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    StatusResponseDto statusResponseDto;

    @PostMapping("consumidor")
    public ResponseEntity<UsuarioResponseDto> addConsumidor(@RequestBody Consumidor consumidor){

        ResponseEntity<UsuarioResponseDto> response = null;

        for (Consumidor aux: consumidorRepository.findAll()) {
            if (consumidor.equals(aux))
                response = ResponseEntity.ok(statusResponseDto.statusFalse());
            else
                response = ResponseEntity.ok(statusResponseDto.statusTrue());
        }
       return response;
    }
}
