package com.agrotech.agro_tech_system.application.usecase.leitura;

import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.repository.LeituraRepository;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import com.agrotech.agro_tech_system.domain.strategy.ValidadorSensorStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrarLeituraUseCase {

    private final SensorRepository sensorRepository;
    private final LeituraRepository leituraRepository;
    private final List<ValidadorSensorStrategy> validadorSensor;

    @Transactional
    public void executar(String sensorId, Double valor, LocalDateTime dataHora){

        var sensor = sensorRepository.buscarPorId(sensorId)
                .orElseThrow(() -> new ValidacaoException("Sensor não encontrado!"));

        Leitura leitura = new Leitura(
                null,
                sensor,
                valor,
                dataHora,
                sensor.getLocalizacao()
        );

        validadorSensor.stream()
                .filter(v -> v.suportar(sensor.getTipo()))
                .forEach(v -> v.validar(leitura));

        leituraRepository.salvar(leitura);
    }
}
