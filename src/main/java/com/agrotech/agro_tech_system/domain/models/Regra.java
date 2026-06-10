package com.agrotech.agro_tech_system.domain.models;

import com.agrotech.agro_tech_system.domain.enums.OperadorRegra;
import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Regra {

    private String id;
    private TipoSensor tipoSensor;
    private OperadorRegra operador;
    private double valor;
    private boolean ativo;

    public Regra(
            String id,
            TipoSensor tipoSensor,
            OperadorRegra operador,
            double valor,
            boolean ativo) {

        if (tipoSensor == null) {
            throw new ValidacaoException("Tipo do sensor é obrigatório");
        }

        if (operador == null) {
            throw new ValidacaoException("Operador é obrigatório");
        }

        if (valor <= 0) {
            throw new ValidacaoException("Valor deve ser maior que zero");
        }

        this.id = id;
        this.tipoSensor = tipoSensor;
        this.operador = operador;
        this.valor = valor;
        this.ativo = ativo;
    }

    // comportamento CORE do sistema
    public boolean deveGerarAlerta(double valorLeitura) {
        if (!ativo) return false;

        return switch (operador) {
            case MAIOR_QUE -> valorLeitura > valor;
            case MENOR_QUE -> valorLeitura < valor;
        };
    }

    public void ativar() { this.ativo = true; }
    public void desativar() { this.ativo = false; }
}
