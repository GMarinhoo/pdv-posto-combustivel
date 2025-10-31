package com.br.pdvpostocombustivel.api.venda;

import com.br.pdvpostocombustivel.api.estoque.EstoqueService;
import com.br.pdvpostocombustivel.api.venda.dto.VendaItemRequest;
import com.br.pdvpostocombustivel.api.venda.dto.VendaItemResponse;
import com.br.pdvpostocombustivel.api.venda.dto.VendaRequest;
import com.br.pdvpostocombustivel.api.venda.dto.VendaResponse;
import com.br.pdvpostocombustivel.domain.entity.*;
import com.br.pdvpostocombustivel.domain.repository.PessoaRepository;
import com.br.pdvpostocombustivel.domain.repository.PrecoRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import com.br.pdvpostocombustivel.domain.repository.VendaRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException;
import com.br.pdvpostocombustivel.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final PessoaRepository pessoaRepository;
    private final ProdutoRepository produtoRepository;
    private final PrecoRepository precoRepository;
    private final EstoqueService estoqueService;

    public VendaService(VendaRepository vendaRepository,
                        PessoaRepository pessoaRepository,
                        ProdutoRepository produtoRepository,
                        PrecoRepository precoRepository,
                        EstoqueService estoqueService) {
        this.vendaRepository = vendaRepository;
        this.pessoaRepository = pessoaRepository;
        this.produtoRepository = produtoRepository;
        this.precoRepository = precoRepository;
        this.estoqueService = estoqueService;
    }

    @Transactional
    public VendaResponse registrarVenda(VendaRequest req) {
        if (req.itens() == null || req.itens().isEmpty()) {
            throw new RegraNegocioException("A venda precisa ter pelo menos 1 item.");
        }

        Pessoa frentista = pessoaRepository.findById(req.idFrentista())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Frentista não encontrado: " + req.idFrentista()));

        Venda venda = new Venda();
        venda.setFrentista(frentista);
        venda.setDataHora(LocalDateTime.now());
        venda.setFormaPagamento(req.formaPagamento());

        BigDecimal valorTotalVenda = BigDecimal.ZERO;

        for (VendaItemRequest itemReq : req.itens()) {
            Produto produto = produtoRepository.findById(itemReq.idProduto())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado: " + itemReq.idProduto()));

            Preco preco = precoRepository.findFirstByProdutoOrderByDataHoraAlteracaoDesc(produto)
                    .orElseThrow(() -> new RegraNegocioException("Produto " + produto.getNome() + " está sem preço cadastrado."));

            BigDecimal precoUnitario = preco.getValor();
            BigDecimal quantidade = itemReq.quantidade();
            BigDecimal precoTotalItem = precoUnitario.multiply(quantidade);

            valorTotalVenda = valorTotalVenda.add(precoTotalItem);

            VendaItem vendaItem = new VendaItem();
            vendaItem.setProduto(produto);
            vendaItem.setQuantidade(quantidade);
            vendaItem.setPrecoUnitario(precoUnitario);
            vendaItem.setPrecoTotal(precoTotalItem);

            venda.adicionarItem(vendaItem);

            estoqueService.darBaixaEstoquePorProduto(produto.getId(), quantidade);
        }

        venda.setValorTotal(valorTotalVenda);
        Venda vendaSalva = vendaRepository.save(venda);

        return toVendaResponse(vendaSalva);
    }

    private VendaResponse toVendaResponse(Venda venda) {
        Pessoa frentista = venda.getFrentista();

        List<VendaItemResponse> itemResponses = venda.getItens().stream()
                .map(this::toVendaItemResponse)
                .collect(Collectors.toList());

        return new VendaResponse(
                venda.getId(),
                venda.getDataHora(),
                venda.getValorTotal(),
                venda.getFormaPagamento(),
                frentista.getId(),
                frentista.getNomeCompleto(),
                itemResponses
        );
    }

    private VendaItemResponse toVendaItemResponse(VendaItem item) {
        Produto produto = item.getProduto();

        return new VendaItemResponse(
                item.getId(),
                produto.getId(),
                produto.getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getPrecoTotal()
        );
    }
}