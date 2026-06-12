package com.agrotech.agro_tech_system.infra.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import com.agrotech.agro_tech_system.infra.persistence.entity.SensorEntity;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaSensorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SensorRepositoryAdapter implements SensorRepository {

	private final JpaSensorRepository jpa = null;
	
	@Override
	public Sensor salvar(Sensor sensor) {
		var savedEntity = jpa.save(toEntity(sensor));
		return toDomain(savedEntity);
	}
	
	@Override
	public Optional<Sensor> buscarPorId(String id){
		return jpa.findById(id).map(this::toDomain);
	}
	
	@Override
	public List<Sensor> buscarTodos(){
		return jpa.findAll()
				  .stream()
				  .map(this::toDomain)
				  .collect(Collectors.toList());
	}

	@Override
	public void deletar(String id) {
		jpa.deleteById(id);
	}
	
	private Sensor toDomain(SensorEntity entity) {
		return new Sensor(
			entity.getId(), entity.getNome(),
			entity.getLocalizacao(),
			entity.isAtivo(), entity.getTipo()
		);
	}
	
	private SensorEntity toEntity(Sensor sensor) {
		return new SensorEntity(
			sensor.getId(), sensor.getNome(),
			sensor.getLocalizacao(),
			sensor.isAtivo(), sensor.getTipo(),
			null, null
		);
	}
}
