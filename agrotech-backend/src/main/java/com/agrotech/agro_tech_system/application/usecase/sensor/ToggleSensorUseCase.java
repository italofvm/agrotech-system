package com.agrotech.agro_tech_system.application.usecase.sensor;

import com.agrotech.agro_tech_system.domain.exception.RecursoNaoEncontradoException;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToggleSensorUseCase {

    private final SensorRepository sensorRepository;

    public void executar(String id) {
        Sensor sensor = sensorRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sensor não encontrado"));

        if (sensor.isAtivo()) {
            sensor.desativar();
        } else {
            sensor.ativar();
        }

        sensorRepository.salvar(sensor);
    }
}
