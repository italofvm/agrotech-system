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
				
			SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
	
		filterChain.doFilter(request, response);
	}
	
	private String recuperarToken(HttpServletRequest request) {
		
		String authorizationHeader = request.getHeader("Authorization");
			
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return null;
		}
		
		return authorizationHeader.substring(7);
	}

}
