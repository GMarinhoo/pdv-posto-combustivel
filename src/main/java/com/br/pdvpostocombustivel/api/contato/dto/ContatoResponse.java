package com.br.pdvpostocombustivel.api.contato.dto;

public record ContatoResponse(
        Long id,
        String telefone,
        String email,
        String endereco
) {
}
