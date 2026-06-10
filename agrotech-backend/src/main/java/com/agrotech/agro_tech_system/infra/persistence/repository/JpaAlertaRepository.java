package com.agrotech.agro_tech_system.infra.persistence.repository;

import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.infra.persistence.entity.AlertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAlertaRepository extends JpaRepository<AlertaEntity, String> {
    List<AlertaEntity> findByStatus(StatusAlerta status);
}
