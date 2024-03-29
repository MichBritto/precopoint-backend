package br.com.precopoint.PrecoPoint.config.security;

import br.com.precopoint.PrecoPoint.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;
    @Value("${forum.jwt.secret}")
    private String secret;

    @Autowired
    GetRolesByUser getRolesByUser;

    public String gerarToken(Authentication authentication) {
        Usuario userLogado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("api-precopoint")
                .setSubject(userLogado.getEmail())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .claim("roles", getRolesByUser.rolesStringByUser(authentication.getName()))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public String getEmailUsuario(String token) {
        Claims claims =  Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
