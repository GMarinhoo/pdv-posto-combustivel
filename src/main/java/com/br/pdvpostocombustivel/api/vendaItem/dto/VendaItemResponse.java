package com.br.pdvpostocombustivel.api.vendaItem.dto;

import java.math.BigDecimal;

public record VendaItemResponse(
        Long id,
        Long idProduto,
        String nomeProduto,
        BigDecimal quantidade,
        BigDecimal precoUnitario,
        BigDecimal precoTotal
) {}