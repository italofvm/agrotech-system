package com.agrotech.agro_tech_system.application.usecase.alerta;


import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.domain.models.Alerta;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.models.Regra;
import com.agrotech.agro_tech_system.domain.repository.AlertaRepository;


@Service
public class GerarAlertaUseCase {

    private final AlertaRepository alertaRepository;

    public GerarAlertaUseCase(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public void executar(Leitura leitura, Regra regra) {

        Alerta alerta = new Alerta(
                UUID.randomUUID().toString(), // id
                leitura,
                regra,
                StatusAlerta.ATIVO,
                LocalDateTime.now()
        );

        alertaRepository.salvar(alerta);
    }
}
