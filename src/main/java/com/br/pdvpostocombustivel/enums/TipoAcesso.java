package com.br.pdvpostocombustivel.enums;

public enum TipoAcesso {

    OPERADOR_CAIXA("Operador de Caixa"),
    GERENTE("Gerente"),
    ADMIN("Administrador");

    private final String descricao;

    private TipoAcesso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}