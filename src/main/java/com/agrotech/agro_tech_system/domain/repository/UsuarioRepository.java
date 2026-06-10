package com.agrotech.agro_tech_system.domain.repository;

import java.util.Optional;

import com.agrotech.agro_tech_system.domain.models.Usuario;


public interface UsuarioRepository {

	Optional<Usuario>buscarPorId(String id);
	
	Optional<Usuario>buscarPorLogin(String login);
	
	void salvar(Usuario usuario);
	
	void deletar(String id);
	
	boolean existeLogin(String login);
}
