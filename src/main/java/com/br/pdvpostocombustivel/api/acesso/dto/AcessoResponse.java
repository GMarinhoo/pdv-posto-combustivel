package com.br.pdvpostocombustivel.api.acesso.dto;

import com.br.pdvpostocombustivel.enums.TipoAcesso;

public record AcessoResponse(
        Long id,
        String usuario,
        TipoAcesso perfil
) {}
