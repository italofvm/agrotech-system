package com.agrotech.agro_tech_system.infra.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agrotech.agro_tech_system.infra.persistence.entity.SensorLocalizacaoEntity;

public interface JpaSensorLocalizacaoRepository extends JpaRepository<SensorLocalizacaoEntity, String> {
	
	Optional<SensorLocalizacaoEntity> findFirstBySensor_IdAndDataFimIsNull(String sensorId);
	
	List<SensorLocalizacaoEntity> findAllBySensor_IdOrderByDataInicioAsc(String sensorId);

	@Query("SELECT s FROM SensorLocalizacaoEntity s WHERE s.sensor.id = :sensorId AND s.dataInicio <= :data AND (s.dataFim IS NULL OR s.dataFim >= :data)")
	Optional<SensorLocalizacaoEntity> buscarPorSensorEData(
		@Param("sensorId") String sensorId, @Param("data") LocalDateTime data);
}
