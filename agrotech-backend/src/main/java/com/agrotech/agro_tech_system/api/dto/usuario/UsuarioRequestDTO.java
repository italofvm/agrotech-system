package com.agrotech.agro_tech_system.api.dto.usuario;

import com.agrotech.agro_tech_system.domain.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
    @NotBlank String login,
    @NotBlank String senha,
    @NotNull UserRole role
    ) { }
