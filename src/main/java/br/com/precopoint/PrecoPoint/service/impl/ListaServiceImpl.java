package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.dto.lista.ListaDonoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.ListaProdutoRepository;
import br.com.precopoint.PrecoPoint.repository.ListaRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.service.ListaService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ListaServiceImpl implements ListaService {

    @Autowired
    ListaRepository listaRepository;

    @Autowired
    ListaProdutoRepository listaProdutoRepository;
    @Autowired
    ConsumidorRepository consumidorRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    StatusService statusService;

    @Override
    public ResponseEntity<StatusResponseDto> criarLsita(ListaDonoDto request) {
        try{
            listaRepository.save(request.toLista(consumidorRepository));
            return ResponseEntity.ok(statusService.usuarioStatusTrue());
        }catch(Exception e){

        }
        return ResponseEntity.ok(statusService.usuarioStatusFalse());
    }

    @Override
    public ResponseEntity<StatusResponseDto> addProduto(ListaProdutoDto request) {
        try{
            listaProdutoRepository.save(request.toLista(produtoRepository,listaRepository));
            return ResponseEntity.ok(statusService.usuarioStatusTrue());
        }catch(Exception e){

        }
        return ResponseEntity.ok(statusService.usuarioStatusFalse());
    }
}
