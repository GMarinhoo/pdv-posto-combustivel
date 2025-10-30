package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.PrecoRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrecoService {

    private final PrecoRepository repository;
    private final ProdutoRepository produtoRepository;

    public PrecoService(PrecoRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    public PrecoResponse create(PrecoRequest req) {
        Produto produto = produtoRepository.findById(req.idProduto())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Produto com código %d não encontrado", req.idProduto())));

        Preco preco = new Preco(req.valor(), LocalDateTime.now(), produto);

        repository.save(preco);
        return toResponse(preco);
    }

    @Transactional(readOnly = true)
    public PrecoResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Preço com código %d não encontrado", id)));
    }

    @Transactional(readOnly = true)
    public List<PrecoResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PrecoResponse update(Long id, PrecoRequest req) {
        Preco preco = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Preço com código %d não encontrado", id)));

        Produto produto = produtoRepository.findById(req.idProduto())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Produto com código %d não encontrado", req.idProduto())));

        preco.setValor(req.valor());
        preco.setProduto(produto);
        preco.setDataHoraAlteracao(LocalDateTime.now());

        repository.save(preco);
        return toResponse(preco);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Preço com código %d não encontrado", id));
        }
        repository.deleteById(id);
    }

    private PrecoResponse toResponse(Preco preco) {
        return new PrecoResponse(
                preco.getId(),
                preco.getValor(),
                preco.getDataHoraAlteracao(),
                preco.getProduto().getId()
        );
    }
}