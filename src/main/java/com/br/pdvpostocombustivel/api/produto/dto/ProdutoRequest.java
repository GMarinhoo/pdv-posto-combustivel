package com.br.pdvpostocombustivel.api.produto.dto;

import com.br.pdvpostocombustivel.enums.TipoProduto;

public record ProdutoRequest(
        String nome,
        String referencia,
        String fornecedor,
        String categoria,
        String marca,
        TipoProduto tipoProduto
) {
}
