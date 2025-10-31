package com.br.pdvpostocombustivel.api.venda.dto;

import java.math.BigDecimal;

public record VendaItemRequest(
        Long idProduto,
        BigDecimal quantidade
) {}