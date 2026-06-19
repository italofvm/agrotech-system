package com.agrotech.agro_tech_system.api.dto.alerta;

public record AlertaSseDTO(
    String tipoSensor,
    String operador,
    double valorLeitura,
    double valorLimite,
    String mensagem
) {}
