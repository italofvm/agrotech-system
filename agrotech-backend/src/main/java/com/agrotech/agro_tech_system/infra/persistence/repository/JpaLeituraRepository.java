package com.agrotech.agro_tech_system.infra.persistence.repository;

import com.agrotech.agro_tech_system.infra.persistence.entity.LeituraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLeituraRepository extends JpaRepository<LeituraEntity, Long> {
    List<LeituraEntity> findBySensorId(String sensorId);
}
