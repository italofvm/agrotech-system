package com.agrotech.agro_tech_system.infra.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agrotech.agro_tech_system.infra.persistence.repository.JpaSensorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SensorLocalizacaoRepositoryAdapter implements SensorLocalizacaoRepository {
	
	private final JpaSensorLocalizacaoRepository jpa;
	private final JpaSensorRepository sensorJpa;
	
	@Override
	public SensorLocalizacao salvar(SensorLocalizacao d) {
		return toDomain(jpa.save(toEntity(d)));
	}
	
	@Override
	public Optional<SensorLocalizacao> buscarAtivaPorSensor(String sensorId){
		return jpa.findFirstBySensor_IdDataInic(sensorId).map(this::toDomain);
	}
	
	@Override
	public List<SensorLocalizacao> buscarTodosPorSensor(String sensorId){
		return jpa.findAllBySensor_IdOrderDataInic(sensorId)
			.stream().map(this::toDomain).toList();
	}
	
	private SensorLocalizacao toDomain(SensorLocalizacaoEntity entity) {
		return new SensorLocalizacao(
			entity.getId(), entity.getSensor().getId(),
			entity.getLocalizacao(), entity.getDataInicio(),
			entity.getDataFim()
		);
	}
	
	private SensorLocalizacaoEntity toEntity(SensorLocalizacao d) {
		var e = new SensorLocalizacaoEntity();
		
		e.setId(d.getId());
		e.setLocalizacao(d.getLocalizacao());
		e.setDataInicio(d.getDataInicio());
		e.setDataFim(d.getDataFim());
		
		e.setSensor(sensorJpa.getReferenceById(d.getSensorId()));
		
		return e;
	}
}
