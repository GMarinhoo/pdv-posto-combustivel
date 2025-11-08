package com.br.pdvpostocombustivel.handler;

import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException;
import com.br.pdvpostocombustivel.exception.PessoaNaoEncontradaException;
import com.br.pdvpostocombustivel.exception.RegraNegocioException;
import com.br.pdvpostocombustivel.exception.SenhaInvalidaException;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice(basePackages = "com.br.pdvpostocombustivel.api")
@Hidden
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({PessoaNaoEncontradaException.class, EntidadeNaoEncontradaException.class})
    public ResponseEntity<ApiErroResponse> handleEntidadeNaoEncontrada(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErroResponse body = new ApiErroResponse(status.value(), ex.getMessage());
        logger.warn("{} Path: {}", ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiErroResponse> handleRegraDeNegocio(RegraNegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErroResponse body = new ApiErroResponse(status.value(), ex.getMessage());
        logger.warn("{} Path: {}", ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<ApiErroResponse> handleSenhaInvalida(SenhaInvalidaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiErroResponse body = new ApiErroResponse(status.value(), ex.getMessage());
        logger.warn("{} Path: {}", ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(body, status);
    }
}