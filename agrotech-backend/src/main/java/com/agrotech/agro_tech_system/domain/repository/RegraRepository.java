package com.agrotech.agro_tech_system.domain.repository;

import java.util.List;
import java.util.Optional;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.models.Regra;


public interface RegraRepository {

    Regra salvar(Regra regra);

    Optional<Regra> buscarPorId(String id);

    List<Regra> buscarTodas();

    List<Regra> buscarPorTipo(TipoSensor tipoSensor); // ✅ ADICIONADO

    List<Regra> buscarAtivasPorTipo(TipoSensor tipoSensor); // ✅ MELHOR

    void deletar(String id);
}
