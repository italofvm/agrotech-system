package com.agrotech.agro_tech_system.domain.repository;

import java.util.List;

import com.agrotech.agro_tech_system.domain.models.Leitura;

public interface LeituraRepository {

	Leitura salvar(Leitura leitura);

	List<Leitura> buscarPorSensor(String sensorId);
}
