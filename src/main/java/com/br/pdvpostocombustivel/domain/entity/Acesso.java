package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.enums.TipoAcesso;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "acesso")
public class Acesso implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 50, nullable = false)
    private String usuario;

    @Column(length = 100, nullable = false)
    private String senha;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAcesso perfil;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", unique = true)
    private Pessoa pessoa;

    public Acesso (String usuario, String senha, TipoAcesso Tipo) {
        this.usuario = usuario;
        this.senha = senha;
        this.perfil = Tipo;
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
    public Pessoa getPessoa() {
        return pessoa;
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
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.perfil.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}