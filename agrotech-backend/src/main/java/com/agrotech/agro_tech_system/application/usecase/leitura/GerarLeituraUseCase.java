
package com.agrotech.agro_tech_system.application.usecase.leitura;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.domain.models.Alerta;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.models.Regra;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.repository.AlertaRepository;
import com.agrotech.agro_tech_system.domain.repository.LeituraRepository;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import com.agrotech.agro_tech_system.domain.service.AlertaService;
import com.agrotech.agro_tech_system.domain.service.RegraService;



@Service
public class GerarLeituraUseCase {

    private final SensorRepository sensorRepository;
    private final LeituraRepository leituraRepository;
    private final RegraService regraService;
    private final AlertaService alertaService;
    private final AlertaRepository alertaRepository;

    public GerarLeituraUseCase(
            SensorRepository sensorRepository,
            LeituraRepository leituraRepository,
            RegraService regraService,
            AlertaService alertaService,
            AlertaRepository alertaRepository) {

        this.sensorRepository = sensorRepository;
        this.leituraRepository = leituraRepository;
        this.regraService = regraService;
        this.alertaService = alertaService;
        this.alertaRepository = alertaRepository;
    }

    public void executar() {

        List<Sensor> sensores = sensorRepository.buscarTodos();
        Random random = new Random();

        for (Sensor sensor : sensores) {

            double valor = random.nextDouble(100);

            Leitura leitura = new Leitura(
                    null,
                    sensor,
                    valor,
                    LocalDateTime.now(),
                    sensor.getLocalizacao()
            );

            leituraRepository.salvar(leitura);

            List<Regra> regrasDisparadas = regraService.validar(leitura);

            for (Regra regra : regrasDisparadas) {

                Alerta alerta = alertaService.gerar(leitura, regra);

                alertaRepository.salvar(alerta);
            }
        }
    }
}

