package com.agrotech.agro_tech_system.domain.models;

import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Area {

    private String id;
    private String nome;
    private String descricao;

    public Area(String id, String nome, String descricao) {

        if (nome == null || nome.isBlank()) {
            throw new ValidacaoException("Nome da área é obrigatório");
        }

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    // comportamento
    public void alterarDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }
}
