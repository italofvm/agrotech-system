package com.agrotech.agro_tech_system.infra.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sensores_localizacao")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class SensorLocalizacaoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "sensor_id", nullable = false)
	private SensorEntity sensor;
	
	@Column(nullable = false)
	private String localizacao;
	
	@Column(nullable = false)
	private LocalDateTime dataInicio;
	
	private LocalDateTime dataFim;
	
	public String getId() { return id; }
	
	public SensorEntity getSensor() { return sensor; }
	
	public String getLocalizacao() { return localizacao; }
	
	public LocalDateTime getDataInicio() { return dataInicio; }
	
	public LocalDateTime getDataFim() { return dataFim; }
	
	public void setId(String id) { this.id = id; }
	
	public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
	
	public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }
	
	public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
	
	public void setSensor(SensorEntity sensor) { this.sensor = sensor; }
}
