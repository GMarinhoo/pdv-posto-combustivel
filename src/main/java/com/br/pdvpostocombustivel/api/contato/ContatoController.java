package com.br.pdvpostocombustivel.api.contato;

import com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contatos")
public class ContatoController {

    private final ContatoService service;

    public ContatoController(ContatoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContatoResponse create(@RequestBody ContatoRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public ContatoResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ContatoResponse> list() {
        return service.listAll();
    }

    @PutMapping("/{id}")
    public ContatoResponse update(@PathVariable Long id, @RequestBody ContatoRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}