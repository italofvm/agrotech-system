package com.agrotech.agro_tech_system.domain.repository;

import java.util.List;
import java.util.Optional;

import com.agrotech.agro_tech_system.domain.models.Sensor;

public interface SensorRepository {

	Sensor salvar(Sensor sensor);
	
	Optional<Sensor> buscarPorId(String id);
	
	List<Sensor> buscarTodos();
	
	void deletar(String id);
}
