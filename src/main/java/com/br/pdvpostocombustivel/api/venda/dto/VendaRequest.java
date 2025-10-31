package com.br.pdvpostocombustivel.api.venda.dto;

import java.util.List;

public record VendaRequest(
        Long idFrentista,
        String formaPagamento,
        List<VendaItemRequest> itens
) {}