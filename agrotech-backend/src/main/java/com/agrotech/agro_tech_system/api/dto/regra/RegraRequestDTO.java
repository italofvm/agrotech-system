package com.agrotech.agro_tech_system.api.dto.regra;

import com.agrotech.agro_tech_system.domain.enums.OperadorRegra;
import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegraRequestDTO (


@NotNull TipoSensor tipoSensor,
    @NotNull OperadorRegra operador,
    @Positive double valor,
    @NotNull String mensagem
) {

}
