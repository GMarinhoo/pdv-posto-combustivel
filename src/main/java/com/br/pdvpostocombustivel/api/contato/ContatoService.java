package com.br.pdvpostocombustivel.api.contato;

import com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import com.br.pdvpostocombustivel.domain.entity.Contato;
import com.br.pdvpostocombustivel.domain.repository.ContatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContatoService {

    private final ContatoRepository repository;

    public ContatoService(ContatoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ContatoResponse create(ContatoRequest req) {
        Contato contato = new Contato(req.telefone(), req.email(), req.endereco());
        repository.save(contato);
        return toResponse(contato);
    }

    public ContatoResponse getById(Long id) {
        Contato contato = repository.findById(id).orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        return toResponse(contato);
    }

    public List<ContatoResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContatoResponse update(Long id, ContatoRequest req) {
        Contato contato = repository.findById(id).orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        contato.setTelefone(req.telefone());
        contato.setEmail(req.email());
        contato.setEndereco(req.endereco());
        repository.save(contato);
        return toResponse(contato);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Contato não encontrado");
        }
        repository.deleteById(id);
    }

    private ContatoResponse toResponse(Contato contato) {
        return new ContatoResponse(
                contato.getId(),
                contato.getTelefone(),
                contato.getEmail(),
                contato.getEndereco()
        );
    }
}