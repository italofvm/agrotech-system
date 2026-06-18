package com.agrotech.agro_tech_system.api.dto.leitura;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LeituraRequestDTO(
        @NotBlank String sensorId,
        @NotNull Double valor,

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime dataHora
) {
}
