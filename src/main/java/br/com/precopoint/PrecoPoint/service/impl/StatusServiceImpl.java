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

    @Override
    public StatusResponseDto produtoRemovidoStatusTrue() {
        statusResponseDto.setMensagem("Produto removido com sucesso.");
        statusResponseDto.setStatus(true);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto produtoRemovidoStatusFalse() {
        statusResponseDto.setMensagem("Falha ao remover produto.");
        statusResponseDto.setStatus(false);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto produtoAtualizadoStatusTrue() {
        statusResponseDto.setMensagem("Produto atulizado com sucesso.");
        statusResponseDto.setStatus(true);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto produtoAtualizadoStatusFalse() {
        statusResponseDto.setMensagem("Falha ao atualizar produto.");
        statusResponseDto.setStatus(false);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto listaStatusTrue() {
        statusResponseDto.setMensagem("Lista criada com sucesso");
        statusResponseDto.setStatus(true);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto listaStatusFalse() {
        statusResponseDto.setMensagem("Erro ao criar lista");
        statusResponseDto.setStatus(false);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto addProdutoListaTrue() {
        statusResponseDto.setMensagem("Produto adicionado a lista");
        statusResponseDto.setStatus(true);
        return statusResponseDto;
    }

    @Override
    public StatusResponseDto addProdutoListaFalse() {
        statusResponseDto.setMensagem("Erro ao adicionar produto a lista");
        statusResponseDto.setStatus(false);
        return statusResponseDto;
    }
}
