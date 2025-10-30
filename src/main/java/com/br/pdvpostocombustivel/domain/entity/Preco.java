package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "preco")
public class Preco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_hora_alteracao", nullable = false)
    private LocalDateTime dataHoraAlteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    public Preco () {
    }

    public Preco (BigDecimal valor, LocalDateTime dataHoraAlteracao, Produto produto) {
        this.valor = valor;
        this.dataHoraAlteracao = dataHoraAlteracao;
        this.produto = produto;
    }

    public Long getId () {
        return id;
    }

    public BigDecimal getValor () {
        return valor;
    }

    public LocalDateTime getDataHoraAlteracao() {
        return dataHoraAlteracao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataHoraAlteracao(LocalDateTime dataHoraAlteracao) {
        this.dataHoraAlteracao = dataHoraAlteracao;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setId(Long id) {
        this.id = id;
    }
}