package com.agrotech.agro_tech_system.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.models.Regra;
import com.agrotech.agro_tech_system.domain.repository.RegraRepository;


@Service
public class RegraService {

    private final RegraRepository regraRepository;

    public RegraService(RegraRepository regraRepository) {
        this.regraRepository = regraRepository;
    }

    public List<Regra> buscarRegrasAtivasPorTipo(TipoSensor tipoSensor) {
        return regraRepository.buscarAtivasPorTipo(tipoSensor);
    }

    public List<Regra> validar(Leitura leitura) {

        TipoSensor tipo = leitura.getSensor().getTipo();

        List<Regra> regras = buscarRegrasAtivasPorTipo(tipo);

        List<Regra> disparadas = regras.stream()
                .filter(regra -> regra.deveGerarAlerta(leitura.getValor()))
                .toList();

        return disparadas;
    }
}

