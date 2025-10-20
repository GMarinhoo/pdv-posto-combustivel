package com.br.pdvpostocombustivel.api.contato.dto;

public record ContatoRequest(
        String telefone,
        String email,
        String endereco
) {
}
