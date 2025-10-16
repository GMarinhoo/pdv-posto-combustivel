package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "custo")
public class Custo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Double imposto;

    @Column(nullable = false)
    private Double custoVariavel;

    @Column(nullable = false)
    private Double custoFixo;

    @Column(nullable = false)
    private Double margemLucro;

    @Column(nullable = false)
    private Date dataProcessamento;

    public Custo (Double imposto, Double custoVariavel, Double custoFixo, Double margemLucro, Date dataProcessamento) {
        this.imposto = imposto;
        this.custoVariavel = custoVariavel;
        this.custoFixo = custoFixo;
        this.margemLucro = margemLucro;
        this.dataProcessamento = dataProcessamento;
    }

    public Custo () {

    }

    public Long getId () {
        return id;
    }

    public Double getImposto () {
        return imposto;
    }

    public Double getCustoVariavel() {
        return custoVariavel;
    }

    public Double getCustoFixo() {
        return custoFixo;
    }

    public Double getMargemLucro() {
        return margemLucro;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setImposto(Double imposto) {
        this.imposto = imposto;
    }

    public void setCustoVariavel(Double custoVariavel) {
        this.custoVariavel = custoVariavel;
    }

    public void setCustoFixo(Double custoFixo) {
        this.custoFixo = custoFixo;
    }

    public void setMargemLucro(Double margemLucro) {
        this.margemLucro = margemLucro;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public void setId(Long id) {
        this.id = id;
    }
}