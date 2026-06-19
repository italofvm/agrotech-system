package com.agrotech.agro_tech_system.application.usecase.alerta;

import com.agrotech.agro_tech_system.api.dto.alerta.AlertaResponseDTO;
import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.domain.models.Alerta;
import com.agrotech.agro_tech_system.domain.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAlertasUseCase {

    private final AlertaRepository alertaRepository;

    public List<AlertaResponseDTO> executar(){
        return alertaRepository.buscarTodos()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AlertaResponseDTO> porStatus(StatusAlerta status){
        return alertaRepository.buscarPorStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AlertaResponseDTO mapToResponse(Alerta alerta){
        return new AlertaResponseDTO(
			alerta.getId(),
			alerta.getStatus(),
			alerta.getDataHora(),
			alerta.getLeitura().getValor(),
			alerta.getRegra().getTipoSensor().name(),
			alerta.getRegra().getOperador().name(),
			alerta.getRegra().getValor()
        );
    }
}
