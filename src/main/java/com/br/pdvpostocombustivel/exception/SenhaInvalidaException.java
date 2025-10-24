package com.br.pdvpostocombustivel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("Usuário ou senha inválidos.");
    }

    public SenhaInvalidaException(String message) {
        super(message);
    }
}