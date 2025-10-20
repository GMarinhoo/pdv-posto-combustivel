package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNome(String nome);

    Optional<Produto> findByReferencia(String referencia);

    Optional<Produto> findByFornecedor(String fornecedor);

    Optional<Produto> findByCategoria(String categoria);

    Optional<Produto> findByMarca(String marca);

    boolean existsByNome(String nome);

    boolean existsByReferencia(String referencia);

    boolean existsByFornecedor(String Fornecedor);

    boolean existsByCategoria(String categoria);

    boolean existsByMarca(String marca);

}
