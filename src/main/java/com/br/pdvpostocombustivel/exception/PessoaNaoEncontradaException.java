package com.br.pdvpostocombustivel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PessoaNaoEncontradaException extends RuntimeException {
    public PessoaNaoEncontradaException(Long id) {
        super(String.format("Não existe um cadastro de pessoa com o código %d", id));
    }

    public PessoaNaoEncontradaException(String message) {
        super(message);
    }
}