package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.usecase.cliente.ObterClienteUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObterClienteUseCaseImpl implements ObterClienteUseCase {

    private final ClienteGateway clienteGateway;

    public ObterClienteUseCaseImpl(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }
        @Override
    public List<Cliente> execute() {
        return clienteGateway.listarTodos();

    }
}
