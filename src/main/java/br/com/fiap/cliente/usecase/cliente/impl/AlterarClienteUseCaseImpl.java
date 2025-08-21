package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.usecase.cliente.AlterarClienteUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AlterarClienteUseCaseImpl implements AlterarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public AlterarClienteUseCaseImpl(ClienteGateway clienteGateway){
        this.clienteGateway = clienteGateway;
    }
    @Override
    public Cliente execute(UUID id, Cliente cliente) {
        cliente.setId(id);
        if(id == null) {
            throw new IllegalArgumentException("ID cliente não pode ser nulo");
        }
        
        clienteGateway.buscarPorId(id)
                .orElseThrow(() -> {
                    throw new ClienteNaoEncontradoException("Cliente não encontrado");
                });
        return clienteGateway.salvar(cliente);
    }
}
