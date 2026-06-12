package com.agrotech.agro_tech_system.infra.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agrotech.agro_tech_system.domain.models.Usuario;
import com.agrotech.agro_tech_system.domain.repository.UsuarioRepository;
import com.agrotech.agro_tech_system.infra.persistence.entity.UsuarioEntity;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepository {
	
	private final JpaUsuarioRepository jpa = null;
	
	public Optional<Usuario> buscarPorId(String id){
		return jpa.findById(id).map(this::toDomain);
	}
	
	public Optional<Usuario> buscarPorLogin(String login){
		return jpa.findByLogin(login).map(this::toDomain);
	}
	
	public void salvar(Usuario usuario) {
		jpa.save(toEntity(usuario));
	}
	
	public void deletar(String id) {
		jpa.deleteById(id);
	}
	
	public boolean existeLogin(String login) {
		return jpa.findByLogin(login).isPresent();
	}
	
	private Usuario toDomain(UsuarioEntity entity) {
		return new Usuario(
			entity.getId(), entity.getUsername(),
			entity.getPassword(), entity.getRole()
		);
	}
	
	private UsuarioEntity toEntity(Usuario usuario) {
		return new UsuarioEntity(
			usuario.getId(), usuario.getUsername(),
			usuario.getPassword(), usuario.getRole()
		);
	}
}
