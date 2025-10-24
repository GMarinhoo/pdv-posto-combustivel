package com.br.pdvpostocombustivel.api.estoque.dto;

import com.br.pdvpostocombustivel.enums.TipoEstoque;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;

public record EstoqueRequest(
        BigDecimal quantidade,
        String localTanque,
        String loteEndereco,
        String loteFabricacao,

        @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
        Date dataValidade,

        TipoEstoque tipo
) {}