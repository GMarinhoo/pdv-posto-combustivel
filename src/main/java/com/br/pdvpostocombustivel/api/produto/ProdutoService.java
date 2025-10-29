package com.br.pdvpostocombustivel.api.produto;

import com.br.pdvpostocombustivel.api.produto.dto.ProdutoRequest;
import com.br.pdvpostocombustivel.api.produto.dto.ProdutoResponse;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException; // <-- Import
import com.br.pdvpostocombustivel.exception.RegraNegocioException; // <-- Import (para o futuro)
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public ProdutoResponse create(ProdutoRequest req) {
        Produto produto = new Produto(
                req.nome(), req.referencia(), req.fornecedor(),
                req.categoria(), req.marca(), req.tipoProduto()
        );
        repository.save(produto);
        return toResponse(produto);
    }

    @Transactional(readOnly = true)
    public ProdutoResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Produto com código %d não encontrado", id)));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProdutoResponse update(Long id, ProdutoRequest req) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Produto com código %d não encontrado", id)));


        produto.setNome(req.nome());
        produto.setReferencia(req.referencia());
        produto.setFornecedor(req.fornecedor());
        produto.setCategoria(req.categoria());
        produto.setMarca(req.marca());
        produto.setTipo(req.tipoProduto());

        repository.save(produto);
        return toResponse(produto);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Produto com código %d não encontrado", id));
        }
        repository.deleteById(id);
    }

    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(), produto.getNome(), produto.getReferencia(),
                produto.getFornecedor(), produto.getCategoria(), produto.getMarca(),
                produto.getTipo()
        );
    }
}