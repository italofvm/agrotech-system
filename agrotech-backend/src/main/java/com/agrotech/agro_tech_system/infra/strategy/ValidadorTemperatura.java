package com.agrotech.agro_tech_system.infra.strategy;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.strategy.ValidadorSensorStrategy;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTemperatura implements ValidadorSensorStrategy {

    @Override
    public boolean suportar(TipoSensor tipo){
        return tipo == TipoSensor.TEMPERATURA;
    }

    @Override
    public void validar(Leitura leitura) {
        if(leitura.getValor() < -10 || leitura.getValor() > 60) {
            throw new ValidacaoException(
                    "Temperatura inválida: " + leitura.getValor() + "°C. Intervalo permitido: -10°C a 60°C"
            );
        }
    }
}
