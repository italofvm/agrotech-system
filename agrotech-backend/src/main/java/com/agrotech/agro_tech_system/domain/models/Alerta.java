package com.agrotech.agro_tech_system.domain.models;

import java.time.LocalDateTime;

import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Alerta {

    private String id;
    private Leitura leitura;
    private Regra regra;
    private StatusAlerta  status;
    private LocalDateTime dataHora;

    public Alerta(
            String id,
            Leitura leitura,
            Regra regra,
            StatusAlerta status,
            LocalDateTime dataHora) {

        if (leitura == null) {
            throw new ValidacaoException("Leitura é obrigatória");
        }

        if (regra == null) {
            throw new ValidacaoException("Regra é obrigatória");
        }

        if (status == null) {
            throw new ValidacaoException("Status é obrigatório");
        }

        if (dataHora == null) {
            throw new ValidacaoException("Data/Hora é obrigatória");
        }

        this.id = id;
        this.leitura = leitura;
        this.regra = regra;
        this.status = status;
        this.dataHora = dataHora;
    }

    public void resolver() {
        this.status = StatusAlerta.RESOLVIDO;
    }

    public boolean isAtivo() {
        return this.status == StatusAlerta.ATIVO;
    }
}
