package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.enums.TipoAcesso;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "acesso")
public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 50, nullable = false)
    private String usuario;

    @Column(length = 10, nullable = false)
    private String senha;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAcesso perfil;

    public Acesso (String usuario, String senha, TipoAcesso Tipo) {
        this.usuario = usuario;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Acesso () {

    }

    public Long getId() {
        return id;
    }

    public String getUsuario () {

        return usuario;
    }

    public String getSenha () {

        return senha;
    }

    public TipoAcesso getPerfil() {
        return perfil;
    }

    public void setUsuario (String usuario) {

        this.usuario = usuario;
    }

    public void setSenha (String senha) {

        this.senha = senha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPerfil(TipoAcesso perfil) {
        this.perfil = perfil;
    }
}