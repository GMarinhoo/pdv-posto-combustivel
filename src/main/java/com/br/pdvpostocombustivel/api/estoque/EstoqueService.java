package com.br.pdvpostocombustivel.api.estoque;

import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueRequest;
import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueResponse;
import com.br.pdvpostocombustivel.domain.entity.Estoque;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.EstoqueRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException;
import com.br.pdvpostocombustivel.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstoqueService {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    public void darBaixaEstoquePorProduto(Long idProduto, BigDecimal quantidadeVendida) {
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado: " + idProduto));

        Estoque estoque = repository.findByProduto(produto)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Estoque não encontrado para o produto: " + produto.getNome()));

        if (estoque.getQuantidade().compareTo(quantidadeVendida) < 0) {
            throw new RegraNegocioException(
                    String.format("Estoque insuficiente para %s. Disponível: %s, Pedido: %s",
                            produto.getNome(), estoque.getQuantidade(), quantidadeVendida)
            );
        }

        BigDecimal novoEstoque = estoque.getQuantidade().subtract(quantidadeVendida);
        estoque.setQuantidade(novoEstoque);
        repository.save(estoque);
    }

    public EstoqueResponse create(EstoqueRequest req) {
        Produto produto = produtoRepository.findById(req.idProduto())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado: " + req.idProduto()));

        Estoque estoque = new Estoque(
                req.quantidade(), req.localTanque(), req.loteEndereco(),
                req.loteFabricacao(), req.dataValidade(), req.tipo(),
                produto
        );

        repository.save(estoque);
        return toResponse(estoque);
    }

    @Transactional(readOnly = true)
    public EstoqueResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Registro de estoque com código %d não encontrado", id)));
    }

    @Transactional(readOnly = true)
    public List<EstoqueResponse> listAll() {
        return repository.findAllComProduto().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EstoqueResponse update(Long id, EstoqueRequest req) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Registro de estoque com código %d não encontrado", id)));

        Produto produto = produtoRepository.findById(req.idProduto())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado: " + req.idProduto()));

        estoque.setProduto(produto);
        estoque.setQuantidade(req.quantidade());
        estoque.setLocalTanque(req.localTanque());
        estoque.setLoteEndereco(req.loteEndereco());
        estoque.setLoteFabricacao(req.loteFabricacao());
        estoque.setDataValidade(req.dataValidade());
        estoque.setTipo(req.tipo());

        repository.save(estoque);
        return toResponse(estoque);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Registro de estoque com código %d não encontrado", id));
        }
        repository.deleteById(id);
    }

    private EstoqueResponse toResponse(Estoque estoque) {
        return new EstoqueResponse(
                estoque.getId(),
                estoque.getProduto().getId(),
                estoque.getQuantidade(),
                estoque.getLocalTanque(),
                estoque.getLoteEndereco(),
                estoque.getLoteFabricacao(),
                estoque.getDataValidade(),
                estoque.getTipo()
        );
    }
}