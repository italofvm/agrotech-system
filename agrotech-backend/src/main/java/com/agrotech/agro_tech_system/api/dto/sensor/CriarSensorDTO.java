package com.agrotech.agro_tech_system.api.dto.sensor;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarSensorDTO(
        @NotBlank
        String nome,

        @NotBlank
        TipoSensor tipo,

        @NotNull
        String localizacao
) {
}
