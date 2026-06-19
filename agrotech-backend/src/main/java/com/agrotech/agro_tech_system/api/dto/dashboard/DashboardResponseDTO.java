package com.agrotech.agro_tech_system.api.dto.dashboard;

import java.time.LocalDateTime;
import java.util.List;

public record DashboardResponseDTO(
    long totalSensores,
    long totalAlertas,
    long alertasAtivos,
    List<LeituraRecenteDTO> leiturasRecentes
) {
	
    public record LeituraRecenteDTO(
        String sensorNome,
        Double valor,
        LocalDateTime dataHora
    ) { }
}
