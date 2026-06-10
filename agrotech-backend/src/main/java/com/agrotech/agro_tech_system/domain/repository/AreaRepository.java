package com.agrotech.agro_tech_system.domain.repository;

import java.util.List;
import java.util.Optional;

import com.agrotech.agro_tech_system.domain.models.Area;

public interface AreaRepository {

    Area salvar(Area area);

    Optional<Area> buscarPorId(String id);

    List<Area> buscarTodos();

    void deletar(String id);
}
