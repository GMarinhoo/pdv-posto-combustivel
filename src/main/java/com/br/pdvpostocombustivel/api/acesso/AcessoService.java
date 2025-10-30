package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import com.br.pdvpostocombustivel.exception.RegraNegocioException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AcessoService {

    private final AcessoRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AcessoService(AcessoRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public AcessoResponse registrar(AcessoRequest req) {
        Optional<Acesso> acessoExistente = repository.findByUsuario(req.usuario());
        if (acessoExistente.isPresent()) {
            throw new RegraNegocioException(String.format("Nome de usuário '%s' já está em uso.", req.usuario()));
        }

        String senhaCriptografada = passwordEncoder.encode(req.senha());

        Acesso novoAcesso = new Acesso(req.usuario(), senhaCriptografada, req.perfil());

        repository.save(novoAcesso);

        return new AcessoResponse(novoAcesso.getId(), novoAcesso.getUsuario(), novoAcesso.getPerfil());
    }
}