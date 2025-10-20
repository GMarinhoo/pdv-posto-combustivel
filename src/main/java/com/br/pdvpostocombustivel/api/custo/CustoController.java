package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/custos")
public class CustoController {

    private final CustoService service;

    public CustoController(CustoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustoResponse create(@RequestBody CustoRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public CustoResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<CustoResponse> list() {
        return service.listAll();
    }

    @PutMapping("/{id}")
    public CustoResponse update(@PathVariable Long id, @RequestBody CustoRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}