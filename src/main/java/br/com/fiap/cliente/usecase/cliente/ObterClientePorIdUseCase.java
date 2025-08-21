package br.com.fiap.cliente.usecase.cliente;

import br.com.fiap.cliente.domain.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ObterClientePorIdUseCase {
    Optional<Cliente> execute(UUID id);
}
