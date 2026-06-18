package com.agrotech.agro_tech_system.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "leituras")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class LeituraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id", nullable = false)
    private SensorEntity sensor;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String localizacao;
    
    public Long getId() { return id; }
    
    public SensorEntity getSensor() { return sensor; }
    
    public Double getValor() { return valor; }
    
    public LocalDateTime getDataHora() { return dataHora; }
    
    public String getLocalizacao() { return localizacao; }
    
    public void setId(Long id) { this.id = id; }
    
    public void setSensor(SensorEntity sensor) { this.sensor = sensor; }
    
    public void setValor(Double valor) { this.valor = valor; }
    
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
}
