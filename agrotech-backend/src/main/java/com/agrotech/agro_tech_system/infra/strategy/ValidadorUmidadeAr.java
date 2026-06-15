package com.agrotech.agro_tech_system.infra.strategy;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.strategy.ValidadorSensorStrategy;
import org.springframework.stereotype.Component;

@Component
public class ValidadorUmidadeAr implements ValidadorSensorStrategy {
    @Override
    public boolean suportar(TipoSensor tipo) {
        return tipo == TipoSensor.UMIDADE_AR;
    }

    @Override
    public void validar(Leitura leitura) {
        if (leitura.getValor() < 0 || leitura.getValor() > 100) {
            throw new ValidacaoException(
                    "Umidade do ar inválida: " + leitura.getValor() + "%. Intervalo permitido: 0% a 100%"
            );
        }
    }
}
