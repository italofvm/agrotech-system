package com.agrotech.agro_tech_system.infra.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.infra.persistence.repository.JpaUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {
	
	private final JpaUsuarioRepository jpa = null;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return jpa.findByLogin(username)
				  .orElseThrow(() -> new UsernameNotFoundException(
						  "Usuário não encontrado: " + username));
	}
}
