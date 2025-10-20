package com.br.pdvpostocombustivel.api.preco.dto;

import java.math.BigDecimal;
import java.util.Date;

public record PrecoResponse(
        Long id,
        BigDecimal valor,
        String dataAlteracao,
        Date horaAlteracao
) {
}
