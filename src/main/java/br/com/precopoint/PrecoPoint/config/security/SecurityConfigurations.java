package br.com.precopoint.PrecoPoint.config.security;

import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity //por padrão, bloqueia todos os endpoints da aplicação
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/auth","/filtro/**","/cadastro/**","/email/**").permitAll()
                .antMatchers("/consumidor/**").hasAnyRole("CONSUMIDOR","ADMINISTRADOR")
                .antMatchers("/lista/**").hasAnyRole("CONSUMIDOR","ADMINISTRADOR")
                .antMatchers("/fornecedor/**").hasAnyRole("FORNECEDOR","ADMINISTRADOR")
                .antMatchers("/produto/**").hasAnyRole("FORNECEDOR","ADMINISTRADOR")
                .antMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .anyRequest()
                .authenticated()
                .and().cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AutenticacaoViaTokenFIlter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }
}
