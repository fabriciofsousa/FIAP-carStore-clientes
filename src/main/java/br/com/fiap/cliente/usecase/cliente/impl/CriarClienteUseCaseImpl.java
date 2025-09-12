package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.usecase.cliente.CriarClienteUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CriarClienteUseCaseImpl implements CriarClienteUseCase {

    private final ClienteGateway clienteGateway;
    private final Validator validator;

    public CriarClienteUseCaseImpl(ClienteGateway clienteGateway, Validator validator) {
        this.clienteGateway = clienteGateway;
        this.validator = validator;
    }


    @Override
    public Cliente execute(Cliente cliente) {
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Erro de validação: ");
            violations.forEach(v -> errorMsg.append(v.getPropertyPath()).append(" ").append(v.getMessage()).append("; "));
            throw new IllegalArgumentException(errorMsg.toString());
        }

        return clienteGateway.salvar(cliente);
    }

}
