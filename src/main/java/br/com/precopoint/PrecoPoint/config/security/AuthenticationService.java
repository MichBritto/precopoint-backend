package br.com.precopoint.PrecoPoint.config.security;

import br.com.precopoint.PrecoPoint.repository.ConsumidorRepository;
import br.com.precopoint.PrecoPoint.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService{

    @Autowired
    ConsumidorRepository consumidorRepository;
    @Autowired
    FornecedorRepository fornecedorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        if(fornecedorRepository.findByEmail(username).isPresent())
            return fornecedorRepository.findByEmail(username).get();
        else if(consumidorRepository.findByEmail(username).isPresent())
            return consumidorRepository.findByEmail(username).get();

        throw new UsernameNotFoundException("Dados Invalidos");
    }


}
