package com.br.pdvpostocombustivel.api.auth;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException;
import com.br.pdvpostocombustivel.exception.SenhaInvalidaException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AcessoRepository acessoRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AcessoRepository acessoRepository, PasswordEncoder passwordEncoder) {
        this.acessoRepository = acessoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AcessoResponse authenticate(LoginRequest loginRequest) {
        Acesso acesso = acessoRepository.findByUsuario(loginRequest.usuario())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Usuário '%s' não encontrado.", loginRequest.usuario())));

        if (!passwordEncoder.matches(loginRequest.senha(), acesso.getSenha())) {
            throw new SenhaInvalidaException();
        }

        return new AcessoResponse(
                acesso.getId(),
                acesso.getUsuario(),
                acesso.getPerfil()
        );
    }
}