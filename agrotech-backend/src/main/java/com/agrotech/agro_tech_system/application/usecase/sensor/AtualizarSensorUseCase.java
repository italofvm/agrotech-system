package com.agrotech.agro_tech_system.application.usecase.sensor;

import com.agrotech.agro_tech_system.domain.exception.RecursoNaoEncontradoException;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import com.agrotech.agro_tech_system.api.dto.sensor.AtualizarSensorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarSensorUseCase {

    private final SensorRepository sensorRepository;

    public void executar(String id, AtualizarSensorDTO dto){
        Sensor sensor = sensorRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sensor não encontrado"));

        String novoNome = dto.nome() != null ? dto.nome() : sensor.getNome();
        String novaLocalizacao = dto.localizacao() != null ? dto.localizacao() : sensor.getLocalizacao();

        Sensor atualizado = new Sensor(
                sensor.getId(),
                novoNome,
                novaLocalizacao,
                sensor.isAtivo(),
                sensor.getTipo()
        );
        
        sensorRepository.salvar(atualizado);
    }
}
