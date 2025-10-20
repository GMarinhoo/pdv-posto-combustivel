package com.br.pdvpostocombustivel.api.produto;



import com.br.pdvpostocombustivel.api.produto.dto.ProdutoRequest;
import com.br.pdvpostocombustivel.api.produto.dto.ProdutoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse create(@RequestBody ProdutoRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public ProdutoResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ProdutoResponse> list() {
        return service.listAll();
    }

    @PutMapping("/{id}")
    public ProdutoResponse update(@PathVariable Long id, @RequestBody ProdutoRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}