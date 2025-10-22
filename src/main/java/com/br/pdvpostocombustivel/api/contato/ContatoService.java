package com.br.pdvpostocombustivel.api.contato;

import com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import com.br.pdvpostocombustivel.domain.entity.Contato;
import com.br.pdvpostocombustivel.domain.repository.ContatoRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException; // <-- Import
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContatoService {

    private final ContatoRepository repository;

    public ContatoService(ContatoRepository repository) {
        this.repository = repository;
    }

    public ContatoResponse create(ContatoRequest req) {
        Contato contato = new Contato(req.telefone(), req.email(), req.endereco());
        repository.save(contato);
        return toResponse(contato);
    }

    @Transactional(readOnly = true)
    public ContatoResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Contato com código %d não encontrado", id))); // <-- Alterado
    }

    @Transactional(readOnly = true)
    public List<ContatoResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ContatoResponse update(Long id, ContatoRequest req) {
        Contato contato = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Contato com código %d não encontrado", id))); // <-- Alterado
        contato.setTelefone(req.telefone());
        contato.setEmail(req.email());
        contato.setEndereco(req.endereco());
        repository.save(contato);
        return toResponse(contato);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Contato com código %d não encontrado", id)); // <-- Alterado
        }
        repository.deleteById(id);
    }

    private ContatoResponse toResponse(Contato contato) {
        return new ContatoResponse(
                contato.getId(), contato.getTelefone(),
                contato.getEmail(), contato.getEndereco()
        );
    }
}