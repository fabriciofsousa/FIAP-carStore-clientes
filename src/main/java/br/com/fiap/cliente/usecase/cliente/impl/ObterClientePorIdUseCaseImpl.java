package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.usecase.cliente.ObterClientePorIdUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ObterClientePorIdUseCaseImpl implements ObterClientePorIdUseCase {

    private final ClienteGateway clienteGateway;

    public ObterClientePorIdUseCaseImpl(ClienteGateway clienteGateway)
    {
        this.clienteGateway = clienteGateway;
    }
    @Override
    public Optional<Cliente> execute(UUID id)
    {
        if (clienteGateway.buscarPorId(id).isEmpty()) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
        return clienteGateway.buscarPorId(id);
    }
}
