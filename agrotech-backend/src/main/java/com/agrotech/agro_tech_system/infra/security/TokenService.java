package com.agrotech.agro_tech_system.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.infra.persistence.entity.UsuarioEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class TokenService {

	@Value("${api.security.secret}")
	private String secret;
	
	public String gerarToken(UsuarioEntity usuario) {
		
		var algoritmo = Algorithm.HMAC256(secret);
		
		return JWT.create()
				  .withIssuer("agro-sensores")
				  .withSubject(usuario.getUsername())
				  .withClaim("role", usuario.getRole().name())
				  .withExpiresAt(dataExpiracao())
				  .sign(algoritmo);
	}
	
	public String getSubject(String token) {
		
		var algoritmo = Algorithm.HMAC256(secret);
		
		return JWT.require(algoritmo)
				  .withIssuer("agro-sensores")
				  .build()
				  .verify(token)
				  .getSubject();
	}
	
	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
