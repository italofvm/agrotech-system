package com.agrotech.agro_tech_system.application.usecase.sensor;

import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarSensores {

    private final SensorRepository sensorRepository;

    public void executar(String id){
        sensorRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Sensor não encontrado!"));

        sensorRepository.deletar(id);
    }
}
