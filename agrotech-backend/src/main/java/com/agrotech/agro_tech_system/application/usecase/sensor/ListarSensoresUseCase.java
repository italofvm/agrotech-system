package com.agrotech.agro_tech_system.application.usecase.sensor;

import com.agrotech.agro_tech_system.api.dto.sensor.SensorResponseDTO;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.models.SensorLocalizacao;
import com.agrotech.agro_tech_system.domain.repository.LeituraRepository;
import com.agrotech.agro_tech_system.domain.repository.SensorLocalizacaoRepository;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListarSensoresUseCase {

    private final SensorRepository sensorRepository;
    private final LeituraRepository leituraRepository;
    private final SensorLocalizacaoRepository sensorLocalizacaoRepository;

    public List<SensorResponseDTO> executar() {
        return sensorRepository.buscarTodos()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public SensorResponseDTO buscarPorId(String id){
        var sensor = sensorRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado!"));

        var leituraDominio = leituraRepository.buscarPorSensor(sensor.getId());

        List<SensorResponseDTO.LeituraResponseDTO> leiturasDTO = leituraDominio.stream()
                .map(leitura -> new SensorResponseDTO.LeituraResponseDTO(
                        leitura.getId(),
                        leitura.getValor(),
                        leitura.getDataHora(),
                        leitura.getLocalizacao()
                )).collect(Collectors.toList());

        List<SensorResponseDTO.HistoricoLocalizacaoDTO> historicoDTO =
                sensorLocalizacaoRepository.buscarTodosPorSensor(sensor.getId())
                        .stream()
                        .map(loc -> new SensorResponseDTO.HistoricoLocalizacaoDTO(
                                loc.getId(),
                                loc.getLocalizacao(),
                                loc.getDataInicio(),
                                loc.getDataFim()
                        )).collect(Collectors.toList());

        return new SensorResponseDTO(
                sensor.getId(),
                sensor.getNome(),
                sensor.getLocalizacao(),
                sensor.getTipo().name(),
                sensor.isAtivo(),
                leiturasDTO,
                historicoDTO
        );
    }

    private SensorResponseDTO mapToResponse(Sensor sensor){

        var leituraDominio = leituraRepository.buscarPorSensor(sensor.getId());

        List<SensorResponseDTO.LeituraResponseDTO> leiturasDTO = leituraDominio.stream()
                .map(
                        leitura -> new SensorResponseDTO.LeituraResponseDTO(
                                leitura.getId(),
                                leitura.getValor(),
                                leitura.getDataHora(),
                                leitura.getLocalizacao()
                        )
                ).collect(Collectors.toList());

        return new SensorResponseDTO(
                sensor.getId(),
                sensor.getNome(),
                sensor.getLocalizacao(),
                sensor.getTipo().name(),
                sensor.isAtivo(),
                leiturasDTO,
                List.of()
        );
    }
}