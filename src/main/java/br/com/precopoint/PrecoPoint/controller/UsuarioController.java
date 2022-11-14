package br.com.precopoint.PrecoPoint.controller;


import br.com.precopoint.PrecoPoint.model.Consumidor;
import br.com.precopoint.PrecoPoint.model.Fornecedor;
import br.com.precopoint.PrecoPoint.model.TipoConta;
import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    ConsumidorRepository consumidorRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @ResponseBody
    @PostMapping("fornecedor")
    public String addFornecedor(){

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCnpj("1234567");
        fornecedor.setLogotipo("http...");
        fornecedor.setEndereco("Rua tal...");
        fornecedor.setNome("Semar");
        fornecedor.setEmail("semar@gmail.com");
        fornecedor.setSenha("1234");
        fornecedor.setTipoConta(TipoConta.FORNECEDOR);
        fornecedorRepository.save(fornecedor);

        return "Fornecedor adicionado com sucesso";
    }

    @ResponseBody
    @PostMapping("consumidor")
    public String addConsumidor(){
        Consumidor consumidor = new Consumidor();
        consumidor.setNome("Michel Brito");
        consumidor.setEmail("michel@gmail.com");
        consumidor.setSenha("4321");
        consumidor.setEndereco("Rua Tal...");
        consumidor.setTipoConta(TipoConta.CONSUMIDOR);

        consumidorRepository.save(consumidor);
        return "Consumidor adicionado com sucesso";
    }
}
