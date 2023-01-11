package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.service.StatusService;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {

    StatusResponseDto statusResponseDto = new StatusResponseDto();

    @Override
    public StatusResponseDto usuarioStatusTrue() {
        statusResponseDto.setMensagem("Cadastramento realizado com sucesso.");
        statusResponseDto.setStatus(true);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto usuarioStatusFalse() {
        statusResponseDto.setMensagem("Falha no cadastramento, revise os dados e tente novamente.");
        statusResponseDto.setStatus(false);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto produtoStatusTrue() {
        statusResponseDto.setMensagem("Produto adicionado com sucesso!");
        statusResponseDto.setStatus(true);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto produtoStatusFalse() {
        statusResponseDto.setMensagem("Falha ao adicionar produto, revise os dados e tente novamente.");
        statusResponseDto.setStatus(false);
        return statusResponseDto;
    }
}
