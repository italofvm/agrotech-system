package com.agrotech.agro_tech_system.infra.persistence.repository;

import com.agrotech.agro_tech_system.infra.persistence.entity.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSensorRepository extends JpaRepository<SensorEntity, String> {
    List<SensorEntity> findByAreaId(String areaId);
}
