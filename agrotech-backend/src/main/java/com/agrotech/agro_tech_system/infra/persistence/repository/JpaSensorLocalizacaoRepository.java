package com.agrotech.agro_tech_system.infra.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.agrotech.agro_tech_system.infra.persistence.entity.SensorLocalizacaoEntity;

public interface JpaSensorLocalizacaoRepository extends JpaRepository<SensorLocalizacaoEntity, String> {
	
	Optional<SensorLocalizacaoEntity> findFirstBySensor_IdDataInic(String sensorId);
	
	List<SensorLocalizacaoEntity> findAllBySensor_IdOrderDataInic(String sensorId);
	
	Optional<SensorLocalizacaoEntity> buscarPorSensorEData(
		@Param("sensorId") String sensorId, @Param("data") LocalDateTime data);
}
