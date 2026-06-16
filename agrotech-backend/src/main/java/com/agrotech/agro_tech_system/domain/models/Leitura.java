package com.agrotech.agro_tech_system.domain.models;

import java.time.LocalDateTime;

import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Leitura {

	private Long id;
	private Sensor sensor;
	private Double valor;
	private LocalDateTime dataHora;
	private String localizacao;
	
	public Leitura(Long id, Sensor sensor, Double valor, LocalDateTime dataHora, String localizacao) {

		if (sensor == null)
			throw new ValidacaoException("Sensor é obrigatório");
		
		if (valor == null)
			throw new ValidacaoException("Valor da leituraé obrigatório");
		
		if (dataHora == null)
			throw new ValidacaoException("Data/Hora é obrigatório");
		
		this.id = id;
		this.sensor = sensor;
		this.valor = valor;
		this.dataHora = dataHora;
		this.localizacao = localizacao;
	}
	
	public Long getId() { return id; }
	
	public Sensor getSensor() { return sensor; }
	
	public Double getValor() { return valor; }
	
	public LocalDateTime getDataHora() { return dataHora; }
	
	public String getLocalizacao() { return localizacao; }
}
