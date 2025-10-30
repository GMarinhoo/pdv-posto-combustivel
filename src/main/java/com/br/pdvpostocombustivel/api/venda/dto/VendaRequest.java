package com.br.pdvpostocombustivel.api.venda.dto;

import com.br.pdvpostocombustivel.api.vendaItem.dto.VendaItemRequest;

import java.util.List;

public record VendaRequest(
        Long idFrentista,
        String formaPagamento,
        List<VendaItemRequest> itens
) {}