package com.agrotech.agro_tech_system.infra.persistence.adapter;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.repository.LeituraRepository;
import com.agrotech.agro_tech_system.infra.persistence.entity.LeituraEntity;
import com.agrotech.agro_tech_system.infra.persistence.entity.SensorEntity;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaLeituraRepository;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaSensorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LeituraRepositoryAdapter implements LeituraRepository {
	
	private final JpaLeituraRepository jpa = null;
	private final JpaSensorRepository sensorJpa = null;
	
	@Override
	public Leitura salvar(Leitura leitura) {
		return toDomain(jpa.save(toEntity(leitura)));
	}
	
	@Override
	public List<Leitura> buscarPorSensor(String sensorId) {
		return jpa.findBySensorId(sensorId).stream().map(this::toDomain).toList();
	}
	
	private Leitura toDomain(LeituraEntity entity) {
		
		SensorEntity se = sensorJpa.findById(
			entity.getSensor().getId())
				  .orElseThrow(() -> new RuntimeException("Sensor não encontrado no DB!"));
		
		Objects.requireNonNull(se.getNome(), "Sensor sem nome no DB: " + se.getId());
					
		var sensor = new Sensor(
			se.getId(), se.getNome(),
			se.getLocalizacao(),
			se.isAtivo(), se.getTipo()
		);
		
		return new Leitura(
			entity.getId(), sensor,
			entity.getValor(),
			entity.getDataHora(),
			entity.getLocalizacao()
		);
	}
	
	private LeituraEntity toEntity(Leitura d) {
		var e = new LeituraEntity();
		
		e.setId(d.getId());
		e.setValor(d.getValor());
		e.setDataHora(d.getDataHora());
		e.setLocalizacao(d.getLocalizacao());
		
		if (d.getSensor() == null || d.getSensor().getId() == null) {
			throw new RuntimeException("Sensor não pode ser nulo na leitura!");
		}
		
		var sensorRef = new SensorEntity();
		sensorRef.setId(d.getSensor().getId());
		e.setSensor(sensorRef);
		
		return e;
	}
}
