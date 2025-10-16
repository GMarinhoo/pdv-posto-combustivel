package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/acessos")
public class AcessoController {

    private final AcessoService service;

    public AcessoController(AcessoService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public AcessoResponse registrar(@RequestBody AcessoRequest req) {
        return service.registrar(req);
    }
}