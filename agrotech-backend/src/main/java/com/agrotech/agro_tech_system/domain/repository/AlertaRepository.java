package com.agrotech.agro_tech_system.domain.repository;

import java.util.List;

import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.domain.models.Alerta;

public interface AlertaRepository {

    Alerta salvar(Alerta alerta);

    List<Alerta> buscarTodos();
    
    List<Alerta> buscarPorStatus(StatusAlerta status);
}