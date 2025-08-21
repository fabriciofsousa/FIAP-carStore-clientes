package br.com.fiap.cliente.exception;

public class ClienteNaoEncontradoException extends RuntimeException{
    public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}
