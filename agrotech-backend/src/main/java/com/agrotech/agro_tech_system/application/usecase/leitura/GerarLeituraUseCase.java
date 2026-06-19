
package com.agrotech.agro_tech_system.application.usecase.leitura;


import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.agrotech.agro_tech_system.api.dto.alerta.AlertaSseDTO;
import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.models.Alerta;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.models.Regra;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.repository.AlertaRepository;
import com.agrotech.agro_tech_system.domain.repository.LeituraRepository;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import com.agrotech.agro_tech_system.domain.service.AlertaService;
import com.agrotech.agro_tech_system.domain.service.RegraService;
import com.agrotech.agro_tech_system.infra.sse.AlertaSseService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GerarLeituraUseCase {

    private final SensorRepository sensorRepository;
    private final LeituraRepository leituraRepository;
    private final RegraService regraService;
    private final AlertaService alertaService;
    private final AlertaRepository alertaRepository;
    private final AlertaSseService alertaSseService;

    public GerarLeituraUseCase(
            SensorRepository sensorRepository,
            LeituraRepository leituraRepository,
            RegraService regraService,
            AlertaService alertaService,
            AlertaRepository alertaRepository,
            AlertaSseService alertaSseService) {

        this.sensorRepository = sensorRepository;
        this.leituraRepository = leituraRepository;
        this.regraService = regraService;
        this.alertaService = alertaService;
        this.alertaRepository = alertaRepository;
        this.alertaSseService = alertaSseService;
    }

    public void executar() {
        List<Sensor> sensores = sensorRepository.buscarTodos();
        for (Sensor sensor : sensores) {
            gerarParaSensor(sensor);
        }
    }

    @Transactional
    public void gerarParaSensor(Sensor sensor) {
        double valor = gerarValorFake(sensor.getTipo());

        Leitura leitura = new Leitura(
                null,
                sensor,
                valor,
                LocalDateTime.now(),
                sensor.getLocalizacao()
        );

        Leitura leituraSalva = leituraRepository.salvar(leitura);

        List<Regra> regrasDisparadas = regraService.validar(leituraSalva);

        for (Regra regra : regrasDisparadas) {
            Alerta alerta = alertaService.gerar(leituraSalva, regra);
            alertaRepository.salvar(alerta);

            alertaSseService.notificar(new AlertaSseDTO(
                    regra.getTipoSensor().name(),
                    regra.getOperador().getSimbolo(),
                    leituraSalva.getValor(),
                    regra.getValor(),
                    regra.getMensagem()
            ));
        }
    }

    /**
     * Gera um valor simulado realista para cada tipo de sensor.
     * Com probabilidade de 10%, gera um pico fora do intervalo normal
     * para acionar as regras de alerta e testar o fluxo completo.
     *
     * Faixas normais e de pico:
     *   TEMPERATURA   → 15–30 °C  | pico: 36–50 °C  (limite da regra: > 35 °C)
     *   UMIDADE_SOLO  → 35–75 %   | pico:  5–25 %   (limite da regra: < 30 %)
     *   UMIDADE_AR    → 45–85 %   (sem regra de alerta ativa)
     *   LUMINOSIDADE  → 10k–70k lux | pico: 82k–95k lux (limite: > 80.000 lux)
     */
    private double gerarValorFake(TipoSensor tipo) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        boolean pico = r.nextDouble() < 0.10; // 10% de chance de pico

        return switch (tipo) {
            case TEMPERATURA  -> arredondar(pico ? r.nextDouble(36, 50)      : r.nextDouble(15, 30));
            case UMIDADE_SOLO -> arredondar(pico ? r.nextDouble(5,  25)      : r.nextDouble(35, 75));
            case UMIDADE_AR   -> arredondar(r.nextDouble(45, 85));
            case LUMINOSIDADE -> arredondar(pico ? r.nextDouble(82_000, 95_000) : r.nextDouble(10_000, 70_000));
        };
    }

    private double arredondar(double valor) {
        return Math.round(valor * 10.0) / 10.0;
    }
}

