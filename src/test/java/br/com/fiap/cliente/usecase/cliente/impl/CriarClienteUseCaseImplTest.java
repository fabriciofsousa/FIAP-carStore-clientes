package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.gateway.cliente.CognitoGateway;
import br.com.fiap.cliente.usecase.cliente.CriarClienteUseCase;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CriarClienteUseCaseImplTest {

    CriarClienteUseCase criarClienteUseCase;

    @Mock
    ClienteGateway clienteGateway;

    @Mock Validator validator;

    @Mock CognitoGateway cognitoGateway;


    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        criarClienteUseCase = new CriarClienteUseCaseImpl(clienteGateway, validator, cognitoGateway);
        when(cognitoGateway.cadastrarUsuario(any(), any(), any())).thenReturn(ClienteResponseDTO.builder().senha("senha123").build());
    }

    @AfterEach
    void teardown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarUmCliente() {
        //ARRANGE
        var cliente = Cliente.builder().id(UUID.randomUUID()).nome("José").cpf("12345678901").build();
        when(clienteGateway.salvar(any(Cliente.class))).thenReturn(cliente);

        //ACT
        ClienteResponseDTO clienteObtido = criarClienteUseCase.execute(cliente);

        //ASSERT
        verify(clienteGateway, times(1)).salvar(any(Cliente.class));
        assertThat(clienteObtido).isNotNull();
        assertThat(cliente.getId().toString()).isEqualTo(clienteObtido.getId().toString());
        assertThat(cliente.getNome()).isEqualTo(clienteObtido.getNome());
        assertThat(cliente.getCpf()).isEqualTo(clienteObtido.getCpf());
    }
}
