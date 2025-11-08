package com.br.pdvpostocombustivel.config;

import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import com.br.pdvpostocombustivel.domain.repository.PessoaRepository;
import com.br.pdvpostocombustivel.enums.TipoAcesso;
import com.br.pdvpostocombustivel.enums.TipoPessoa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (acessoRepository.count() == 0) {

            System.out.println(">>> [DataInitializer] Populando banco de dados com usuário padrão...");

            Pessoa frentistaAdmin = new Pessoa(
                    "Frentista Admin Padrão",
                    "000.000.000-00",
                    12345L,
                    LocalDate.now().minusYears(25),
                    TipoPessoa.FISICA
            );

            Pessoa pessoaSalva = pessoaRepository.save(frentistaAdmin);

            Acesso acesso = new Acesso(
                    "admin",
                    passwordEncoder.encode("123456"),
                    TipoAcesso.ADMIN
            );
            acesso.setPessoa(pessoaSalva);

            acessoRepository.save(acesso);

            System.out.println(">>> [DataInitializer] Usuário 'admin' (Pessoa ID: " + pessoaSalva.getId() + ") criado com sucesso!");

        } else {
            System.out.println(">>> [DataInitializer] Banco de dados já populado.");
        }
    }
}