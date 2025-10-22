package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import com.br.pdvpostocombustivel.exception.RegraNegocioException; // <-- Import
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import necessário
import java.util.Optional; // Import necessário

@Service
@Transactional
public class AcessoService {

    private final AcessoRepository repository;

    public AcessoService(AcessoRepository repository) {
        this.repository = repository;
    }

    public AcessoResponse registrar(AcessoRequest req) {
        // Validação de usuário duplicado
        Optional<Acesso> acessoExistente = repository.findByusuario(req.usuiario());
        if (acessoExistente.isPresent()) {
            throw new RegraNegocioException(String.format("Nome de usuário '%s' já está em uso.", req.usuiario())); // <-- Usa RegraNegocioException
        }

        Acesso novoAcesso = new Acesso(req.usuiario(), req.senha(), req.perfil()); // Assumindo enum TipoAcesso como 'perfil'
        repository.save(novoAcesso);
        return new AcessoResponse(novoAcesso.getId(), novoAcesso.getUsuario(), novoAcesso.getPerfil()); // Assumindo enum TipoAcesso como 'perfil'
    }
}