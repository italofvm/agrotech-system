package com.agrotech.agro_tech_system.infra.persistence.repository;

import com.agrotech.agro_tech_system.infra.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, String> {

    Optional<UsuarioEntity> findByLogin(String login);
}
