package com.agrotech.agro_tech_system.infra.strategy;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.strategy.ValidadorSensorStrategy;
import org.springframework.stereotype.Component;

@Component
public class ValidadorLuminosidade implements ValidadorSensorStrategy {
    @Override
    public boolean suportar(TipoSensor tipo) {
        return tipo == TipoSensor.LUMINOSIDADE;
    }

    @Override
    public void validar(Leitura leitura) {
        if (leitura.getValor() < 0 || leitura.getValor() > 100000) {
            throw new ValidacaoException(
                    "Luminosidade inválida: " + leitura.getValor() + " lux. Intervalo permitido: 0 a 100.000 lux"
            );
        }
    }
}
