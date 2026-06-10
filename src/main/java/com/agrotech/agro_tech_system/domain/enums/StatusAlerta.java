package com.agrotech.agro_tech_system.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status do alerta")
public enum StatusAlerta {

    @Schema(description = "Alerta ativo")
    ATIVO("ativo"),

    @Schema(description = "Alerta resolvido")
    RESOLVIDO("resolvido");

    private String status;

    StatusAlerta(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static StatusAlerta fromStatus(String status) {
        for (StatusAlerta s : values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + status);
    }
}
