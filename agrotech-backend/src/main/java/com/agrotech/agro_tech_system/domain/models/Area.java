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
    private boolean ativo;

    public Area(String id, String nome, String descricao, boolean ativo) {

        if (nome == null || nome.isBlank()) {
            throw new ValidacaoException("Nome da área é obrigatório");
        }

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public void alterarNome(String novoNome) {
        if (novoNome == null || novoNome.isBlank()) {
            throw new ValidacaoException("Nome da área é obrigatório");
        }
        this.nome = novoNome;
    }

    public void alterarDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }
}
