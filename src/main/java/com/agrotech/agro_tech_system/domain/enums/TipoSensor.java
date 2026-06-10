package com.agrotech.agro_tech_system.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de sensores disponíveis")
public enum TipoSensor {

    @Schema(description = "Temperatura em graus Celsius")
    TEMPERATURA("temperatura"),

    @Schema(description = "Umidade do solo")
    UMIDADE_SOLO("umidade_solo"),

    @Schema(description = "Umidade do ar")
    UMIDADE_AR("umidade_ar"),

    @Schema(description = "Luminosidade em lux")
    LUMINOSIDADE("luminosidade");

    private String tipo;

    TipoSensor(String tipo) {
        this.tipo = tipo;
    }

    @JsonValue
    public String getTipo() {
        return tipo;
    }

    @JsonCreator
    public static TipoSensor fromTipo(String tipo) {
        for (TipoSensor t : values()) {
            if (t.tipo.equalsIgnoreCase(tipo)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de sensor inválido: " + tipo);
    }
}
