package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    Optional<Contato> findByTelefone(String telefone);

    Optional<Contato> findByEmail(String email);

    boolean existsByTelefone(String telefone);

    boolean existsByEmail(String email);
}
