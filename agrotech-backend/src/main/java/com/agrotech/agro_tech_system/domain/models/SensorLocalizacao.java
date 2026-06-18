package com.agrotech.agro_tech_system.domain.models;

import java.time.LocalDateTime;

import com.agrotech.agro_tech_system.domain.exception.RegraNegocioException;

import lombok.Getter;

@Getter
public class SensorLocalizacao {

	private String id;
	private String sensorId;
	private String localizacao;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	
	public SensorLocalizacao(String id, String sensorId, String localizacao, LocalDateTime dataInicio, LocalDateTime dataFim) {
		
		if (sensorId == null) throw new RegraNegocioException("Sensor é obrigatório!");
		
		if (localizacao == null || localizacao.isBlank()) 
			throw new RegraNegocioException("Localização é obrigatória!");
		
		if (dataInicio == null) throw new RegraNegocioException("Data de início é obrigatória!");
		
		this.id = id;
		this.sensorId = sensorId;
		this.localizacao = localizacao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}
	
	public void encerrar(LocalDateTime dataEncerramento) {
		if (this.dataFim != null)
			throw new RegraNegocioException("Esta localização já foi encerrada!");
		
		this.dataFim = dataEncerramento;
	}
	
	public boolean isAtivo() { return this.dataFim == null; }
	
	public String getId() { return id; }
	
	public String getSensorId() { return sensorId; }
	
	public String getLocalizacao() { return localizacao; }
	
	public LocalDateTime getDataInicio() { return dataInicio; }
	
	public LocalDateTime getDataFim() { return dataFim; }
}
