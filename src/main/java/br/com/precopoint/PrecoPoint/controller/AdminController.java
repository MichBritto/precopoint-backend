package br.com.precopoint.PrecoPoint.controller;

import br.com.precopoint.PrecoPoint.service.ConsumidorService;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import br.com.precopoint.PrecoPoint.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    ConsumidorService consumidorService;
    @Autowired
    FornecedorService fornecedorService;
    @Autowired
    LoggingService loggingService;

    @GetMapping("get-all-consumidor")
    public ResponseEntity<?> getAllConsumidor(){
        return consumidorService.getAllConsumidor();
    }

    @GetMapping("get-all-fornecedor")
    public ResponseEntity<?> getAllFornecedor(){
        return fornecedorService.getAllFornecedor();
    }

    @GetMapping("logs")
    public ResponseEntity<?> getLogs(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return loggingService.getLogging(pageable);
    }
}
