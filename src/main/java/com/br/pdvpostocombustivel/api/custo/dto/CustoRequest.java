package com.br.pdvpostocombustivel.api.custo.dto;

import java.util.Date;

public record CustoRequest(
        Double imposto,
        Double custoVariavel,
        Double custoFixo,
        Double margemLucro,
        Date dataProcessameto
) {
}
