package com.agrotech.agro_tech_system.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.domain.models.Alerta;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.models.Regra;
import com.agrotech.agro_tech_system.domain.repository.AlertaRepository;

@Service

public class AlertaService {

    public Alerta gerar(Leitura leitura, Regra regra) {

        Alerta alerta = new Alerta(
                UUID.randomUUID().toString(), // id
                leitura,                      // leitura
                regra,                        // regra
                StatusAlerta.ATIVO,           // status
                LocalDateTime.now()           // dataHora
        );

        return alerta;
    }
}


