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

    // CREATE
    public PessoaResponse create(PessoaRequest req) {
        validarUnicidadeCpfCnpj(req.cpfCnpj(), null);

        Pessoa novaPessoa = toEntity(req);
        repository.save(novaPessoa); // Salva a entidade
        return toResponse(novaPessoa); // Retorna o DTO da entidade salva (com ID)
    }

    // READ by ID
    @Transactional(readOnly = true)
    public PessoaResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse) // Se encontrar, mapeia para DTO
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));
    }

    // READ by CPF/CNPJ
    @Transactional(readOnly = true)
    public PessoaResponse getByCpfCnpj(String cpfCnpj) {
        return repository.findByCpfCnpj(cpfCnpj)
                .map(this::toResponse) // Se encontrar, mapeia para DTO
                .orElseThrow(() -> new PessoaNaoEncontradaException(String.format("Nenhuma pessoa encontrada com CPF/CNPJ '%s'.", cpfCnpj)));
    }

    // LIST paginado
    @Transactional(readOnly = true)
    public Page<PessoaResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    // UPDATE
    public PessoaResponse update(Long id, PessoaRequest req) {
        Pessoa p = repository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));

        // Valida unicidade apenas se o CPF/CNPJ foi alterado
        if (req.cpfCnpj() != null && !req.cpfCnpj().equals(p.getCpfCnpj())) {
            validarUnicidadeCpfCnpj(req.cpfCnpj(), id);
        }

        // Atualiza os dados da entidade existente
        p.setNomeCompleto(req.nomeCompleto());
        p.setCpfCnpj(req.cpfCnpj());
        p.setNumeroCtps(req.numeroCtps());
        p.setDataNascimento(req.dataNascimento());
        p.setTipoPessoa(req.tipoPessoa());

        repository.save(p); // Salva a entidade atualizada
        return toResponse(p);
    }

    // PATCH
    public PessoaResponse patch(Long id, PessoaRequest req) {
        Pessoa p = repository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));

        // Aplica atualizações parciais
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

        repository.save(p); // Salva a entidade atualizada
        return toResponse(p);
    }

    // DELETE
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new PessoaNaoEncontradaException(id);
        }
        repository.deleteById(id);
    }

    // ---------- Helpers ----------
    private void validarUnicidadeCpfCnpj(String cpfCnpj, Long idAtual) {
        Optional<Pessoa> existente = repository.findByCpfCnpj(cpfCnpj);
        if (existente.isPresent() && (idAtual == null || !existente.get().getId().equals(idAtual))) {
            throw new RegraNegocioException("CPF/CNPJ já cadastrado: " + cpfCnpj);
        }
    }

    private Pessoa toEntity(PessoaRequest req) {
        // Certifique-se que sua entidade Pessoa tem este construtor
        return new Pessoa(
                req.nomeCompleto(),
                req.cpfCnpj(),
                req.numeroCtps(),
                req.dataNascimento(),
                req.tipoPessoa()
        );
    }

    private PessoaResponse toResponse(Pessoa p) {
        // Certifique-se que seu DTO PessoaResponse tem estes campos na ordem correta
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