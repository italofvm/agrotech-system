package com.agrotech.agro_tech_system.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.api.dto.sensor.*;
import com.agrotech.agro_tech_system.infra.persistence.entity.UsuarioEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class TokenService {

	@Value("${api.security.secret:default-dev-secret-123456}")
	private String secret;
	
	public String gerarToken(UsuarioEntity usuario) {
		
		Algorithm algoritomo = Algorithm.HMAC256(secret);
		return JWT.create()
				.withIssuer("agrotech-system") // emissor de token
				.withSubject(usuario.getLogin()) // dados de Login do usuario
				.withClaim("role", usuario.getRole().name()) // acesso a role do usuario no token
				.withExpiresAt(dataExpiracao()) // este é a data de experiaçãodo token
				.sign(algoritomo); // criação do token é assinada 
	}
	
	
	private Instant dataExpiracao() {
		return LocalDateTime.now()
				.plusHours(2)
				.toInstant(ZoneOffset.of("-03:00"));
		
	}
	
	public String getSubject(String token) {
		Algorithm algoritimo = Algorithm.HMAC256(secret);
		
		return JWT.require(algoritimo)
				.withIssuer("agrotech-system")
				.build()
				.verify(token)
				.getSubject();
	}
}
