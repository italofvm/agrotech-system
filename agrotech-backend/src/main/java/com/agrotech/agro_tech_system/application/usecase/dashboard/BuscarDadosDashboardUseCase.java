package com.agrotech.agro_tech_system.application.usecase.dashboard;


import com.agrotech.agro_tech_system.api.dto.dashboard.DashboardResponseDTO;
import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.domain.repository.AlertaRepository;
import com.agrotech.agro_tech_system.domain.repository.LeituraRepository;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarDadosDashboardUseCase {

    private final SensorRepository sensorRepository;
    private final AlertaRepository  alertaRepository;
    private final LeituraRepository  leituraRepository;

    public DashboardResponseDTO executar() {

        long totalSensores = sensorRepository.buscarTodos().size();

        var todosAlertas = alertaRepository.buscarTodos();
        long totalAlertas = todosAlertas.size();
        long alertasAtivos = alertaRepository.buscarPorStatus(StatusAlerta.ATIVO).size();

        List<DashboardResponseDTO.LeituraRecenteDTO> leiturasRecentes = sensorRepository.buscarTodos()
                .stream()
                .flatMap(sensor -> leituraRepository.buscarPorSensor(sensor.getId())
                        .stream()
                        .limit(1)
                        .map(leitura -> new DashboardResponseDTO.LeituraRecenteDTO(
                                sensor.getNome(),
                                leitura.getValor(),
                                leitura.getDataHora()
                )))
                .toList();

        return new DashboardResponseDTO(
                totalSensores,
                totalAlertas,
                alertasAtivos,
                leiturasRecentes
        );
    }
}