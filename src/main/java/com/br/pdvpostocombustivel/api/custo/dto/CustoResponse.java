package com.br.pdvpostocombustivel.api.custo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public record CustoResponse(
        Long id,
        Double imposto,
        Double custoVariavel,
        Double custoFixo,
        Double margemLucro,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dataProcessamento
) {
}
