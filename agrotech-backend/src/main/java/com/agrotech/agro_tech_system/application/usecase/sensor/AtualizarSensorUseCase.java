package com.agrotech.agro_tech_system.application.usecase.sensor;

import com.agrotech.agro_tech_system.domain.exception.RecursoNaoEncontradoException;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.models.SensorLocalizacao;
import com.agrotech.agro_tech_system.domain.repository.SensorLocalizacaoRepository;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import com.agrotech.agro_tech_system.api.dto.sensor.AtualizarSensorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AtualizarSensorUseCase {

    private final SensorRepository sensorRepository;
    private final SensorLocalizacaoRepository sensorLocalizacaoRepository;

    public void executar(String id, AtualizarSensorDTO dto){
        Sensor sensor = sensorRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sensor não encontrado"));

        String novoNome = dto.nome() != null ? dto.nome() : sensor.getNome();
        String novaLocalizacao = dto.localizacao() != null ? dto.localizacao() : sensor.getLocalizacao();

        boolean localizacaoMudou = dto.localizacao() != null
                && !dto.localizacao().equals(sensor.getLocalizacao());

        if (localizacaoMudou) {
            LocalDateTime agora = LocalDateTime.now();

            // Encerra o registro de localização atual (se existir)
            sensorLocalizacaoRepository.buscarAtivaPorSensor(id).ifPresent(atual -> {
                atual.encerrar(agora);
                sensorLocalizacaoRepository.salvar(atual);
            });

            // Cria novo registro de localização
            sensorLocalizacaoRepository.salvar(new SensorLocalizacao(
                    null, id, novaLocalizacao, agora, null
            ));
        }

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
