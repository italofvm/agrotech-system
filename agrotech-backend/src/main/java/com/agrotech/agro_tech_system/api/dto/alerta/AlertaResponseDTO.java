package com.agrotech.agro_tech_system.api.dto.alerta;

import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;

import java.time.LocalDateTime;

public record AlertaResponseDTO(
        String id,
        StatusAlerta status,
        LocalDateTime dataHora,
        Double valorLeitura,
        String tipoSensor,
        String operador,
        double ValorLimite
) {
}
