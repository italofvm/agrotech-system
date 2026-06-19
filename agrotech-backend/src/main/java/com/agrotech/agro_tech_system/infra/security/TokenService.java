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

	@Value("${api.security.secret:default-dev-secret-123456}")
	private String secret;
	
	public String gerarToken(UsuarioEntity usuario) {
		
		Algorithm algoritmo = Algorithm.HMAC256(secret);
		return JWT.create()
			.withIssuer("agrotech-system")
			.withSubject(usuario.getLogin())
			.withClaim("role", usuario.getRole().name())
			.withExpiresAt(dataExpiracao())
			.sign(algoritmo); 
	}
	
	private Instant dataExpiracao() {
		return LocalDateTime.now()
			.plusHours(2)
			.toInstant(ZoneOffset.of("-03:00"));
		
	}
	
	public String getSubject(String token) {
		Algorithm algoritmo = Algorithm.HMAC256(secret);
		
		return JWT.require(algoritmo)
			.withIssuer("agrotech-system")
			.build()
			.verify(token)
			.getSubject();
	}
}
