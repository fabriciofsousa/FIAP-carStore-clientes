package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.usecase.cliente.CriarClienteUseCase;
import org.springframework.stereotype.Service;

@Service
public class CriarClienteUseCaseImpl implements CriarClienteUseCase {

    private final ClienteGateway clienteGateway;
    public CriarClienteUseCaseImpl(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }


    @Override
    public Cliente execute(Cliente cliente)
    {
        return clienteGateway.salvar(cliente);
    }
}
