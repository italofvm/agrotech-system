package com.agrotech.agro_tech_system.infra.persistence.repository;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.infra.persistence.entity.RegraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaRegraRepository extends JpaRepository<RegraEntity, String> {
    List<RegraEntity> findByAtivo(boolean ativo);
    List<RegraEntity> findByTipoSensor(TipoSensor tipoSensor);
    List<RegraEntity> findByTipoSensorAndAtivoTrue(TipoSensor tipoSensor);
}
