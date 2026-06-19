package com.agrotech.agro_tech_system.api.dto.regra;

import com.agrotech.agro_tech_system.domain.enums.OperadorRegra;
import com.agrotech.agro_tech_system.domain.enums.TipoSensor;

public record RegraResponseDTO(
    String id,
    TipoSensor tipoSensor,
    OperadorRegra operador,
    double valor,
    boolean ativo,
    String mensagem
) { }
