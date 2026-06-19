package com.agrotech.agro_tech_system.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.domain.models.Alerta;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.models.Regra;

@Service

public class AlertaService {

    public Alerta gerar(Leitura leitura, Regra regra) {

        Alerta alerta = new Alerta(
                null,                // id gerado pelo JPA (evita merge() no Hibernate 7)
                leitura,
                regra,
                StatusAlerta.ATIVO,
                LocalDateTime.now()
        );

        return alerta;
    }
}


