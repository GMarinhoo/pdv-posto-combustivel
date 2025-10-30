package com.br.pdvpostocombustivel.api.auth;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse; // <-- Import importante
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AcessoResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        var dadosUsuarioAutenticado = authService.authenticate(loginRequest);
        return ResponseEntity.ok(dadosUsuarioAutenticado);
    }
}