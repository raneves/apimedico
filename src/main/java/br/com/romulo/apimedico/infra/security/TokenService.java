package br.com.romulo.apimedico.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.romulo.apimedico.dominio.entidade.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")     //cuidado import do Spring e nao do lombok
	private String secret;
	
	public String gerarToken(Usuario usuario) { 
		System.out.println(secret);
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer("API medico")
                .withSubject(usuario.getLogin())
                .withExpiresAt(dataExpiracao()) //duracao de 2 horas, de expiracao do token
                .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerrar token jwt", exception);
        }		
    }
	
	private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
	
	public String getSubject(String tokenJWT) {
	    try {
	        var algoritmo = Algorithm.HMAC256(secret);
	        return JWT.require(algoritmo)
	                        .withIssuer("API medico")
	                        .build()
	                        .verify(tokenJWT)
	                        .getSubject();
	    } catch (JWTVerificationException exception) {
	        throw new RuntimeException("Token JWT inválido ou expirado!");
	    }
	}
}
