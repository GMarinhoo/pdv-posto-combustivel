package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.enums.TipoProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 30, nullable = false)
    private String nome;

    @Column(length = 200, nullable = false)
    private String referencia;

    @Column(length = 200, nullable = false)
    private String fornecedor;

    @Column(length = 50, nullable = false)
    private String categoria;

    @Column(length = 50, nullable = false)
    private String marca;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_combustivel", nullable = false)
    private TipoProduto tipo;

    public Produto (String nome, String referencia, String fornecedor, String categoria, String marca, TipoProduto tipo) {
        this.nome = nome;
        this.referencia = referencia;
        this.fornecedor = fornecedor;
        this.categoria = categoria;
        this.marca = marca;
        this.tipo = tipo;
    }

    public Produto () {

    }

    public Long getId() {
        return id;
    }

    public String getNome () {
        return nome;
    }

    public String getReferencia () {
        return referencia;
    }

    public String getFornecedor () {
        return fornecedor;
    }

    public String getCategoria () {
        return categoria;
    }

    public String getMarca () {
        return marca;
    }

    public TipoProduto getTipo() {
        return tipo;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public void setReferencia (String referencia) {
        this.referencia = referencia;
    }

    public void setFornecedor (String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setCategoria (String categoria) {
        this.categoria = categoria;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }
}