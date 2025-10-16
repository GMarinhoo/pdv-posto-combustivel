package com.br.pdvpostocombustivel.api.estoque.dto;

import com.br.pdvpostocombustivel.enums.TipoEstoque;

import java.math.BigDecimal;
import java.util.Date;

public record EstoqueResponse(
        Long id,
        BigDecimal quantidade,
        String localTanque,
        String loteEndereco,
        String loteFabricacao,
        Date dataValidade,
        TipoEstoque tipoEstoque
) {
}
