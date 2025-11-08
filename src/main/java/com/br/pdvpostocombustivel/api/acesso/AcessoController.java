package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public List<AcessoResponse> listar() {
        return service.listarTodos();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    }

    @PatchMapping("/{id}/perfil")
    public AcessoResponse atualizarPerfil(@PathVariable Long id,
                                          @RequestBody com.br.pdvpostocombustivel.enums.TipoAcesso novoPerfil) {
        return service.atualizarPerfil(id, novoPerfil);
    }
}