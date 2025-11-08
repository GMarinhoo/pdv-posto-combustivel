package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import com.br.pdvpostocombustivel.domain.repository.PessoaRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException;
import com.br.pdvpostocombustivel.exception.RegraNegocioException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AcessoService {

    private final AcessoRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final PessoaRepository pessoaRepository;

    public AcessoService(AcessoRepository repository, PasswordEncoder passwordEncoder, PessoaRepository pessoaRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.pessoaRepository = pessoaRepository;
    }

    public AcessoResponse registrar(AcessoRequest req) {

        Optional<Acesso> acessoExistente = repository.findByUsuario(req.usuario());
        if (acessoExistente.isPresent()) {
            throw new RegraNegocioException(String.format("Nome de usuário '%s' já está em uso.", req.usuario()));
        }

        Pessoa pessoaVinculada = pessoaRepository.findById(req.idPessoa())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada para o ID: " + req.idPessoa()));
        String senhaCriptografada = passwordEncoder.encode(req.senha());
        Acesso novoAcesso = new Acesso(req.usuario(), senhaCriptografada, req.perfil());
        novoAcesso.setPessoa(pessoaVinculada);

        repository.save(novoAcesso);

        return new AcessoResponse(
                novoAcesso.getId(),
                novoAcesso.getUsuario(),
                novoAcesso.getPerfil(),
                pessoaVinculada.getId(),
                pessoaVinculada.getNomeCompleto()
        );
    }

    @Transactional(readOnly = true)
    public List<AcessoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponse) // Usa o novo helper
                .collect(java.util.stream.Collectors.toList());
    }

    public void excluirPorId(Long id) {
        Acesso acesso = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Acesso com ID " + id + " não encontrado."));
        repository.delete(acesso);
    }

    public AcessoResponse atualizarPerfil(Long id, com.br.pdvpostocombustivel.enums.TipoAcesso novoPerfil) {
        Acesso acesso = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Acesso com ID " + id + " não encontrado."));

        acesso.setPerfil(novoPerfil);
        Acesso acessoSalvo = repository.save(acesso);
        return toResponse(acessoSalvo);
    }

    private AcessoResponse toResponse(Acesso acesso) {
        Pessoa pessoa = acesso.getPessoa();
        Long idPessoa = null;
        String nomePessoa = "Pessoa não vinculada";

        if (pessoa != null) {
            idPessoa = pessoa.getId();
            nomePessoa = pessoa.getNomeCompleto();
        }

        return new AcessoResponse(
                acesso.getId(),
                acesso.getUsuario(),
                acesso.getPerfil(),
                idPessoa,
                nomePessoa
        );
    }
}