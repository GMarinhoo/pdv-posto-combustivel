package com.br.pdvpostocombustivel.api.acesso.dto;

import com.br.pdvpostocombustivel.enums.TipoAcesso;
import com.br.pdvpostocombustivel.enums.TipoPessoa;

import java.time.LocalDate;

public record RegistroCompletoRequest(
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        LocalDate dataNascimento,
        TipoPessoa tipoPessoa,

        String usuario,
        String senha,
        TipoAcesso perfil
) {}