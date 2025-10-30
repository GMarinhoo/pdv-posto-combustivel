package com.br.pdvpostocombustivel.api.vendaItem.dto;

import java.math.BigDecimal;

public record VendaItemRequest(
        Long idProduto,
        BigDecimal quantidade
) {}