package com.br.pdvpostocombustivel.api.auth;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.api.auth.LoginRequest;
import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException;
import com.br.pdvpostocombustivel.exception.SenhaInvalidaException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AcessoRepository acessoRepository;

    public AuthService(AcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    public AcessoResponse authenticate(LoginRequest loginRequest) {
        Acesso acesso = acessoRepository.findByUsuario(loginRequest.usuario())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Usuário '%s' não encontrado.", loginRequest.usuario())));

        if (!loginRequest.senha().equals(acesso.getSenha())) {
            throw new SenhaInvalidaException();
        }

        return new AcessoResponse(
                acesso.getId(),
                acesso.getUsuario(),
                acesso.getPerfil()
        );
    }
}