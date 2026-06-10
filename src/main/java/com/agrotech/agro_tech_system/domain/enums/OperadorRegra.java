package com.agrotech.agro_tech_system.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Operadores de comparação para regras")
public enum OperadorRegra {

    @Schema(description = "Maior que (>)")
    MAIOR_QUE(">"),

    @Schema(description = "Menor que (<)")
    MENOR_QUE("<");

    private String simbolo;

    OperadorRegra(String simbolo) {
        this.simbolo = simbolo;
    }

    @JsonValue
    public String getSimbolo() {
        return simbolo;
    }

    @JsonCreator
    public static OperadorRegra fromSimbolo(String simbolo) {
        for (OperadorRegra op : values()) {
            if (op.simbolo.equals(simbolo)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Operador inválido: " + simbolo);
    }
}
