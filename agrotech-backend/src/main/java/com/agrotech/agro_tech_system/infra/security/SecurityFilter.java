package com.agrotech.agro_tech_system.infra.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.agrotech.agro_tech_system.infra.persistence.repository.JpaUsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter{

	private final TokenService tokenService;
	private final JpaUsuarioRepository repository;
	
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
	
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		if(token != null) {
			
		String subject = tokenService.getSubject(token);
		var login = repository.findByLogin(subject);
		
		if(login.isPresent()) {
			var usuario = login.get();
			var authentication = new UsernamePasswordAuthenticationToken(
					usuario,
					null,
					usuario.getAuthorities()
					);
			
			SecurityContextHolder.getContext()
			    .setAuthentication(authentication);
		}
	  }
		filterChain.doFilter(request, response);
		
	}
	
	private String recuperarToken(HttpServletRequest request) {
	//	1ª buscar o header/cabeçalho da requisição - Authorization
		String authorizationHeader = request.getHeader("Authorization");
		
	//  2ªse o var (authorizationHeader) estiver vazia ou começar com Bearer vamos retornar null	
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return null;
		}
		
	//  3ª Caso contrario, romever a string Bearer e ficar somento com o codigo, que na vdd é o token gerado	
		return authorizationHeader.substring(7);
	}

}
