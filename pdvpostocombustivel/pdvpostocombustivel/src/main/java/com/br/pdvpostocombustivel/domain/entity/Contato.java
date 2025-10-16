package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contato")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 11, nullable = false)
    private String telefone;

    @Column(length = 320, nullable = false)
    private String email;

    @Column(length = 200, nullable = false)
    private String endereco;


    public Contato (String telefone, String email, String endereco) {
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public Contato () {

    }

    public Long getId() {
        return id;
    }

    public String getTelefone () {
        return telefone;
    }

    public String getEmail () {
        return email;
    }

    public String getEndereco () {
        return endereco;
    }

    public void setTelefone (String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setId(Long id) {
        this.id = id;
    }
}