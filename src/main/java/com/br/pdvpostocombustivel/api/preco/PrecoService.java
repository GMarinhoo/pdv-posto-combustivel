package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.repository.PrecoRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException; // <-- Import
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrecoService {

    private final PrecoRepository repository;

    public PrecoService(PrecoRepository repository) {
        this.repository = repository;
    }

    public PrecoResponse create(PrecoRequest req) {
        Preco preco = new Preco(req.valor(), req.dataAlteracao(), req.horaAlteracao());
        repository.save(preco);
        return toResponse(preco);
    }

    @Transactional(readOnly = true)
    public PrecoResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Preço com código %d não encontrado", id))); // <-- Alterado
    }

    @Transactional(readOnly = true)
    public List<PrecoResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PrecoResponse update(Long id, PrecoRequest req) {
        Preco preco = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Preço com código %d não encontrado", id))); // <-- Alterado
        preco.setValor(req.valor());
        preco.setDataAlteracao(req.dataAlteracao());
        preco.setHoraAlteracao(req.horaAlteracao());
        repository.save(preco);
        return toResponse(preco);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Preço com código %d não encontrado", id)); // <-- Alterado
        }
        repository.deleteById(id);
    }

    private PrecoResponse toResponse(Preco preco) {
        return new PrecoResponse(
                preco.getId(), preco.getValor(),
                preco.getDataAlteracao(), preco.getHoraAlteracao()
        );
    }
}