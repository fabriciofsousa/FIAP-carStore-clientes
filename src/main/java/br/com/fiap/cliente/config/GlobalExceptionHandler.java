package br.com.fiap.cliente.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.fiap.cliente.exception.ClienteNaoEncontradoException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
