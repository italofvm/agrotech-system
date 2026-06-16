package com.agrotech.agro_tech_system.infra.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.infra.persistence.repository.JpaUsuarioRepository;

import lombok.RequiredArgsConstructor;

	@Service
	@RequiredArgsConstructor
	public class AutenticacaoService  implements UserDetailsService{

		//1ª Declarar a DI necessaria para o procedimento de Autenticação
		private final JpaUsuarioRepository jpa;
		
		@Override
		public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException{
			return jpa.findByLogin(username)
					.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado: " + username));
			
		}
	}

