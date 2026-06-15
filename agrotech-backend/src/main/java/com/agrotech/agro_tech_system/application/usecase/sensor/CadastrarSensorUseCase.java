package com.agrotech.agro_tech_system.application.usecase.sensor;

import com.agrotech.agro_tech_system.api.dto.sensor.CriarSensorDTO;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarSensorUseCase {

    private final SensorRepository sensorRepository;

    @Transactional
    public Sensor executar(CriarSensorDTO criarSensorDTO){
        Sensor novoSensor = new Sensor(
                null,
                criarSensorDTO.nome(),
                criarSensorDTO.localizacao(),
                true,
                criarSensorDTO.tipo()
        );

        Sensor sensorSalvo = sensorRepository.salvar(novoSensor);

        return sensorSalvo;
    }
}
