package com.br.pdvpostocombustivel.api.estoque;

import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueRequest;
import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/estoques")
public class EstoqueController {

    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueResponse create(@RequestBody EstoqueRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public EstoqueResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<EstoqueResponse> list() {
        return service.listAll();
    }

    @PutMapping("/{id}")
    public EstoqueResponse update(@PathVariable Long id, @RequestBody EstoqueRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}