package com.agrotech.agro_tech_system.domain.models;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Sensor {

	public Sensor (String id, String nome, String localizacao,
			boolean ativo, TipoSensor tipo) {
		
		if (nome == null || nome.isBlank())
			throw new ValidacaoException("Nome do sensor é obrigatório");
		
		if (localizacao == null || localizacao.isBlank()) 
			throw new ValidacaoException("Localização é obrigatória");
		
		if (tipo == null)
			throw new ValidacaoException("Tipo do sensor é obrigatório");
		
		this.id = id;
		this.nome = nome;
		this.localizacao = localizacao;
		this.ativo = ativo;
		this.tipo = tipo;
	}
	
	private String id;
	
	private String nome;
	
	private String localizacao;
	
	private boolean ativo;
	
	private TipoSensor tipo;
	
	
	public void alterarLocalizacao(String novaLocalizacao) {
		
		if(novaLocalizacao ==  null || novaLocalizacao.isBlank()) {
			throw new ValidacaoException("A nova localização não pode ser vazia");
		}
				
		localizacao = novaLocalizacao;
	}
	
	public String getId() { return id; }
	
	public String getNome() { return nome; }
	
	public String getLocalizacao() { return localizacao; }
	
	public TipoSensor getTipo() { return tipo; }
	
	public boolean isAtivo() { return ativo; }
	
	public void ativar() { ativo = true; }
	
	public void desativar () { ativo = false; }
}
