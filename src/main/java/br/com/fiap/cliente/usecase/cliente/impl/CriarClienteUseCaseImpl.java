package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.gateway.cliente.CognitoGateway;
import br.com.fiap.cliente.usecase.cliente.CriarClienteUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;

import static br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO.mapper;

@Service
public class CriarClienteUseCaseImpl implements CriarClienteUseCase {

    private final ClienteGateway clienteGateway;
    private final Validator validator;
    private final CognitoGateway cognitoGateway;

    public CriarClienteUseCaseImpl(ClienteGateway clienteGateway, Validator validator, CognitoGateway cognitoGateway) {
        this.clienteGateway = clienteGateway;
        this.validator = validator;
        this.cognitoGateway = cognitoGateway;
    }


    @Override
    public ClienteResponseDTO execute(Cliente cliente) {
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Erro de validação: ");
            violations.forEach(v -> errorMsg.append(v.getPropertyPath()).append(" ").append(v.getMessage()).append("; "));
            throw new IllegalArgumentException(errorMsg.toString());
        }
        Cliente existente = clienteGateway.buscarPorEmail(cliente.getEmail());
        if (existente != null) {
            throw new IllegalArgumentException("Cliente com email " + cliente.getEmail() + " já existe.");
        }
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO = cognitoGateway.cadastrarUsuario(cliente.getEmail(), cliente.getNome(), clienteResponseDTO);

        return mapper(clienteGateway.salvar(cliente), clienteResponseDTO.getSenha());
    }

}
