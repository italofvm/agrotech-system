package com.agrotech.agro_tech_system.api.dto.leitura;

import java.time.LocalDateTime;

public record LeituraResponseDTO(
    Long id,
    String sensorId,
    String sensorNome,
    Double valor,
    LocalDateTime dataHora,
    String localizacao
) {}
