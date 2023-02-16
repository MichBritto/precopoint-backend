package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.lista.ListaDonoDto;
import br.com.precopoint.PrecoPoint.dto.lista.ListaProdutoDto;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.ConsumidorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.model.Consumidor;
import br.com.precopoint.PrecoPoint.model.Lista;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.ListaProdutoRepository;
import br.com.precopoint.PrecoPoint.repository.ListaRepository;
import br.com.precopoint.PrecoPoint.repository.ProdutoRepository;
import br.com.precopoint.PrecoPoint.service.ListaService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaServiceImpl implements ListaService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
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
    @Autowired
    AuthenticationController authenticationController;
    @Override
    public ResponseEntity<StatusResponseDto> criarLsita(ListaDonoDto request) {
        try{
            listaRepository.save(request.toLista(consumidorRepository));
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.listaStatusTrue().getMensagem());
            return ResponseEntity.ok(statusService.listaStatusTrue());
        }catch(Exception e){
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.listaStatusFalse().getMensagem());
        }
        ThreadContext.put("user", authenticationController.getUser());
        logger.info(statusService.listaStatusFalse().getMensagem());
        return ResponseEntity.badRequest().body(statusService.listaStatusFalse());
    }

    @Override
    public ResponseEntity<StatusResponseDto> addProduto(ListaProdutoDto request) {
        try{
            listaProdutoRepository.save(request.toLista(produtoRepository,listaRepository));
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.addProdutoListaTrue().getMensagem());
            return ResponseEntity.ok(statusService.addProdutoListaTrue());
        }catch(Exception e){
            ThreadContext.put("user", authenticationController.getUser());
            logger.info(statusService.addProdutoListaFalse().getMensagem());
        }
        ThreadContext.put("user", authenticationController.getUser());
        logger.info(statusService.addProdutoListaFalse().getMensagem());
        return ResponseEntity.badRequest().body(statusService.addProdutoListaFalse());
    }

    @Override
    public ResponseEntity<List<?>> getListaConsumidor(ConsumidorResponseDto consumidor) throws Exception {

        try{
            Optional<Consumidor> response = consumidorRepository.findByEmail(consumidor.getEmail());
            return ResponseEntity.ok(listaRepository.findAllByConsumidor(response.get()));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
