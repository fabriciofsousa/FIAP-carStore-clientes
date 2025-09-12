package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.usecase.cliente.AlterarClienteUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class AlterarClienteUseCaseImpl implements AlterarClienteUseCase {

    private final ClienteGateway clienteGateway;
    private final Validator validator;

    public AlterarClienteUseCaseImpl(ClienteGateway clienteGateway, Validator validator){
        this.clienteGateway = clienteGateway;
        this.validator = validator;
    }
    @Override
    public Cliente execute(UUID id, Cliente cliente) {
        cliente.setId(id);

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Erro de validação: ");
            violations.forEach(v -> errorMsg.append(v.getPropertyPath()).append(" ").append(v.getMessage()).append("; "));
            throw new IllegalArgumentException(errorMsg.toString());
        }

        if(id == null) {
            throw new IllegalArgumentException("ID cliente não pode ser nulo");
        }
        
        clienteGateway.buscarPorId(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));
        return clienteGateway.salvar(cliente);
    }
}
