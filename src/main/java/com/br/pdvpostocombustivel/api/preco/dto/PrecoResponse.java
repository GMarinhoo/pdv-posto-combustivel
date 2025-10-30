package com.br.pdvpostocombustivel.api.preco.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PrecoResponse(
        Long id,
        BigDecimal valor,
        LocalDateTime dataHoraAlteracao,
        Long idProduto
) {
}
