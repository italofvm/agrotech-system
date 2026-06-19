package com.agrotech.agro_tech_system.domain.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enum para definir os papéis do usuário", allowableValues = {"ADMIN","OPERADOR"})
public enum UserRole {

    @Schema(description = "Administrador")
    ADMIN("admin"),

    @Schema(description = "Usuário comum")
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }

    @JsonCreator
    public static UserRole fromRole(String role) {
        for (UserRole r : values()) {
            if (r.role.equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Role inválida: " + role);
    }
}

