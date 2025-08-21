package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.usecase.cliente.DeletarClienteUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarClienteUseCaseImpl implements DeletarClienteUseCase {
    private final ClienteGateway clienteGateway;

    public DeletarClienteUseCaseImpl(ClienteGateway clienteGateway)
    {
        this.clienteGateway = clienteGateway;
    }

    @Override
    public void execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cliente não pode ser nulo");
        }
        if (!clienteGateway.buscarPorId(id).isPresent()) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
        clienteGateway.deletar(id);
    }
}
