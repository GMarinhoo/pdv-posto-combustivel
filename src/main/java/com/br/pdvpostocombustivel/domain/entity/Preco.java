package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "preco")
public class Preco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(length = 10, nullable = false)
    private String dataAlteracao;

    @Column(nullable = false)
    private Date horaAlteracao;

    public Preco (BigDecimal valor, String dataAlteracao, Date horaAlteracao) {
        this.valor = valor;
        this.dataAlteracao = dataAlteracao;
        this.horaAlteracao = horaAlteracao;
    }

    public Preco () {

    }

    public Long getId () {
        return id;
    }

    public BigDecimal getValor () {
        return valor;
    }

    public String getDataAlteracao() {
        return dataAlteracao;
    }

    public Date getHoraAlteracao() {
        return horaAlteracao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataAlteracao(String dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public void setHoraAlteracao(Date horaAlteracao) {
        this.horaAlteracao = horaAlteracao;
    }

    public void setId(Long id) {
        this.id = id;
    }
}