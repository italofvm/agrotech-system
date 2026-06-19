package com.agrotech.agro_tech_system.api.dto.leitura;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LeituraRequestDTO(
    @NotBlank String sensorId,
    @NotNull Double valor,        
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull LocalDateTime dataHora
) { }
