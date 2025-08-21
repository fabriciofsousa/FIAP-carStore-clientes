package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.usecase.cliente.CriarClienteUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CriarClienteUseCaseImplTest {

    CriarClienteUseCase criarClienteUseCase;

    @Mock
    ClienteGateway clienteGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        criarClienteUseCase = new CriarClienteUseCaseImpl(clienteGateway);
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
        Cliente clienteObtido = criarClienteUseCase.execute(cliente);

        //ASSERT
        verify(clienteGateway, times(1)).salvar(any(Cliente.class));
        assertThat(clienteObtido).isNotNull();
        assertThat(cliente.getId()).isEqualTo(clienteObtido.getId());
        assertThat(cliente.getNome()).isEqualTo(clienteObtido.getNome());
        assertThat(cliente.getCpf()).isEqualTo(clienteObtido.getCpf());
    }
}
