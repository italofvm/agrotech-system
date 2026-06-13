package com.agrotech.agro_tech_system.api.dto.sensor;

import jakarta.validation.constraints.NotBlank;

public record AtualizarSensorDTO(
        @NotBlank
        String nome
) {
}
