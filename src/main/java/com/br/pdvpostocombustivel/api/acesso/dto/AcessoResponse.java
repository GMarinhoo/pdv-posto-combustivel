package com.br.pdvpostocombustivel.api.acesso.dto;

import com.br.pdvpostocombustivel.enums.TipoAcesso;

public record AcessoResponse(
        Long idAcesso,
        String usuario,
        TipoAcesso perfil,
        Long idPessoa,
        String nomePessoa
) {}
