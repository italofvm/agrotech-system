package com.agrotech.agro_tech_system.domain.repository;

import java.util.List;
import java.util.Optional;

import com.agrotech.agro_tech_system.domain.models.Regra;

public interface RegraRepository {


	Regra salvar(Regra regra);

    Optional<Regra> buscarPorId(String id);

    List<Regra> buscarTodas();

    void deletar(String id);

}
