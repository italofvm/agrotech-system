package com.agrotech.agro_tech_system.infra.persistence.repository;

import com.agrotech.agro_tech_system.infra.persistence.entity.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAreaRepository extends JpaRepository<AreaEntity, String> {
}
