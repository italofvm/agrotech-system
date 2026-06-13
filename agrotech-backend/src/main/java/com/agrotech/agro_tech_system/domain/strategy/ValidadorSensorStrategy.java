package com.agrotech.agro_tech_system.domain.strategy;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.models.Leitura;

public interface ValidadorSensorStrategy {
    boolean suportar(TipoSensor tipo);
    void validar(Leitura  leitura);
}
