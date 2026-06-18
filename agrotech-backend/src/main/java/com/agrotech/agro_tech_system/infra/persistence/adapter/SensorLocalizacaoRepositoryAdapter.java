package com.agrotech.agro_tech_system.infra.persistence.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agrotech.agro_tech_system.domain.models.SensorLocalizacao;
import com.agrotech.agro_tech_system.domain.repository.SensorLocalizacaoRepository;
import com.agrotech.agro_tech_system.infra.persistence.entity.SensorLocalizacaoEntity;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaSensorLocalizacaoRepository;
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
		return jpa.findFirstBySensor_IdAndDataFimIsNull(sensorId).map(this::toDomain);
	}
	
	@Override
	public List<SensorLocalizacao> buscarTodosPorSensor(String sensorId) {
		return jpa.findAllBySensor_IdOrderByDataInicioAsc(sensorId)
			.stream().map(this::toDomain).toList();
	}
	
	@Override
	public Optional<SensorLocalizacao> buscarPorSensorEData(String sensorId, LocalDateTime data) {
		return jpa.buscarPorSensorEData(sensorId, data).map(this::toDomain);
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
