package com.agrotech.agro_tech_system.api.dto.area;

import jakarta.validation.constraints.NotBlank;

public record CriarAreaDTO(
    @NotBlank String nome,
    String descricao
) { }
