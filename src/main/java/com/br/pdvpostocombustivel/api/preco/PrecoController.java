package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/precos")
public class PrecoController {

    private final PrecoService service;

    public PrecoController(PrecoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrecoResponse create(@RequestBody PrecoRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public PrecoResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PrecoResponse> list() {
        return service.listAll();
    }

    @PutMapping("/{id}")
    public PrecoResponse update(@PathVariable Long id, @RequestBody PrecoRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}