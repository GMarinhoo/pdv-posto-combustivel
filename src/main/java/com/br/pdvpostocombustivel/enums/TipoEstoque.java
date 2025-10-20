package com.br.pdvpostocombustivel.enums;

public enum TipoEstoque {

    OK("Em estoque"),
    BAIXO("Estoque baixo"),
    CRITICO("Estoque crítico"),
    INDISPONIVEL("Indisponível");

    private final String descricao;

    private TipoEstoque(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}