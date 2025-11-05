package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Estoque;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByQuantidade(BigDecimal quantidade);

    Optional<Estoque> findByLoteEndereco(String loteEndereco);

    Optional<Estoque> findByLoteFabricacao(String loteFabricacao);

    boolean existsByQuantidade(BigDecimal quantidade);

    boolean existsByLoteEndereco(String loteEndereco);

    boolean existsByLoteFabricacao(String loteFabricacao);

    Optional<Estoque> findByProduto(Produto produto);

    @Query("SELECT e FROM Estoque e JOIN FETCH e.produto")
    List<Estoque> findAllComProduto();
}