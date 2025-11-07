package com.br.pdvpostocombustivel.api.pessoa;

import com.br.pdvpostocombustivel.api.pessoa.dto.PessoaRequest;
import com.br.pdvpostocombustivel.api.pessoa.dto.PessoaResponse;
import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import com.br.pdvpostocombustivel.domain.repository.PessoaRepository;
import com.br.pdvpostocombustivel.exception.PessoaNaoEncontradaException;
import com.br.pdvpostocombustivel.exception.RegraNegocioException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public PessoaResponse create(PessoaRequest req) {
        validarUnicidadeCpfCnpj(req.cpfCnpj(), null);

        Pessoa novaPessoa = toEntity(req);
        repository.save(novaPessoa);
        return toResponse(novaPessoa);
    }

    @Transactional(readOnly = true)
    public PessoaResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));
    }

    @Transactional(readOnly = true)
    public PessoaResponse getByCpfCnpj(String cpfCnpj) {
        return repository.findByCpfCnpj(cpfCnpj)
                .map(this::toResponse)
                .orElseThrow(() -> new PessoaNaoEncontradaException(String.format("Nenhuma pessoa encontrada com CPF/CNPJ '%s'.", cpfCnpj)));
    }

    @Transactional(readOnly = true)
    public Page<PessoaResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    public PessoaResponse update(Long id, PessoaRequest req) {
        Pessoa p = repository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));

        if (req.cpfCnpj() != null && !req.cpfCnpj().equals(p.getCpfCnpj())) {
            validarUnicidadeCpfCnpj(req.cpfCnpj(), id);
        }

        p.setNomeCompleto(req.nomeCompleto());
        p.setCpfCnpj(req.cpfCnpj());
        p.setNumeroCtps(req.numeroCtps());
        p.setDataNascimento(req.dataNascimento());
        p.setTipoPessoa(req.tipoPessoa());

        repository.save(p);
        return toResponse(p);
    }

    public PessoaResponse patch(Long id, PessoaRequest req) {
        Pessoa p = repository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));

        if (req.nomeCompleto() != null) p.setNomeCompleto(req.nomeCompleto());
        if (req.cpfCnpj() != null) {
            if (!req.cpfCnpj().equals(p.getCpfCnpj())) {
                validarUnicidadeCpfCnpj(req.cpfCnpj(), id);
            }
            p.setCpfCnpj(req.cpfCnpj());
        }
        if (req.numeroCtps() != null) p.setNumeroCtps(req.numeroCtps());
        if (req.dataNascimento() != null) p.setDataNascimento(req.dataNascimento());
        if (req.tipoPessoa() != null) p.setTipoPessoa(req.tipoPessoa());

        repository.save(p);
        return toResponse(p);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new PessoaNaoEncontradaException(id);
        }
        repository.deleteById(id);
    }

    private void validarUnicidadeCpfCnpj(String cpfCnpj, Long idAtual) {
        Optional<Pessoa> existente = repository.findByCpfCnpj(cpfCnpj);
        if (existente.isPresent() && (idAtual == null || !existente.get().getId().equals(idAtual))) {
            throw new RegraNegocioException("CPF/CNPJ já cadastrado: " + cpfCnpj);
        }
    }

    private Pessoa toEntity(PessoaRequest req) {
        return new Pessoa(
                req.nomeCompleto(),
                req.cpfCnpj(),
                req.numeroCtps(),
                req.dataNascimento(),
                req.tipoPessoa()
        );
    }

    private PessoaResponse toResponse(Pessoa p) {
        return new PessoaResponse(
                p.getId(),
                p.getNomeCompleto(),
                p.getCpfCnpj(),
                p.getNumeroCtps(),
                p.getDataNascimento(),
                p.getTipoPessoa()
        );
    }
}