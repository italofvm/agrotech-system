package com.agrotech.agro_tech_system.domain.models;

import com.agrotech.agro_tech_system.domain.enums.UserRole;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "senha")
public class Usuario {

	private String id;
	private String login;
	private String senha;
	private UserRole role;
	
	public Usuario(String id, String login, String senha, UserRole role) {
		if (login == null || login.isBlank()) {
			throw new ValidacaoException("Sua credencial de login é obrigatoria!");
		}
		
		if(senha == null || senha.length() < 6) {
			throw new ValidacaoException("Sua senha deve ter pelo menos 6 caracteres!");
		}
		
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.role = role;
	}

	public boolean isAdmin() {
		return this.role == UserRole.ADMIN;
	}
}
