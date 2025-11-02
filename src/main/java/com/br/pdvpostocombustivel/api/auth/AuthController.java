package com.br.pdvpostocombustivel.api.auth;

import com.br.pdvpostocombustivel.api.acesso.AcessoService;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.api.pessoa.PessoaService;
import com.br.pdvpostocombustivel.api.pessoa.dto.PessoaRequest;
import com.br.pdvpostocombustivel.api.pessoa.dto.PessoaResponse;
import com.br.pdvpostocombustivel.api.acesso.dto.RegistroCompletoRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final PessoaService pessoaService;
    private final AcessoService acessoService;

    public AuthController(AuthService authService, PessoaService pessoaService, AcessoService acessoService) {
        this.authService = authService;
        this.pessoaService = pessoaService;
        this.acessoService = acessoService;
    }

    @PostMapping("/login")
    public ResponseEntity<AcessoResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        var dadosUsuarioAutenticado = authService.authenticate(loginRequest);
        return ResponseEntity.ok(dadosUsuarioAutenticado);
    }

    @PostMapping("/registrar-completo")
    @Transactional
    public ResponseEntity<AcessoResponse> registrarCompleto(@RequestBody @Valid RegistroCompletoRequest request) {

        PessoaRequest pessoaRequest = new PessoaRequest(
                request.nomeCompleto(),
                request.cpfCnpj(),
                request.numeroCtps(),
                request.dataNascimento(),
                request.tipoPessoa()
        );
        PessoaResponse pessoaSalva = pessoaService.create(pessoaRequest);
        AcessoRequest acessoRequest = new AcessoRequest(
                request.usuario(),
                request.senha(),
                request.perfil(),
                pessoaSalva.id()
        );
        AcessoResponse acessoSalvo = acessoService.registrar(acessoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(acessoSalvo);
    }
}