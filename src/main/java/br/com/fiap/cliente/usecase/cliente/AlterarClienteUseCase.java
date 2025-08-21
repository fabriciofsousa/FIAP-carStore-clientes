package br.com.fiap.cliente.usecase.cliente;

import br.com.fiap.cliente.domain.Cliente;

import java.util.UUID;

public interface AlterarClienteUseCase {
    public Cliente execute(UUID id, Cliente cliente);

}
