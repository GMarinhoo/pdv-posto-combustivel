package com.br.pdvpostocombustivel.api.estoque.dto;

import com.br.pdvpostocombustivel.enums.TipoEstoque;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record EstoqueResponse(
        Long id,
        Long idProduto,
        BigDecimal quantidade,
        String localTanque,
        String loteEndereco,
        String loteFabricacao,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataValidade,

        TipoEstoque tipo
) {}