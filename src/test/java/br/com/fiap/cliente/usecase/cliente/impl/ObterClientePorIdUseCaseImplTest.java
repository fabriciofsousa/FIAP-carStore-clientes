package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

class ObterClientePorIdUseCaseImplTest {

    ObterClientePorIdUseCaseImpl obterClientePorIdUseCaseImpl;
    @Mock
    ClienteGateway clienteGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        obterClientePorIdUseCaseImpl = new ObterClientePorIdUseCaseImpl(clienteGateway);
    }

    @AfterEach
    void teardown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirObterUmClientePeloId() {
        // Arrange
        Cliente cliente = new Cliente(UUID.randomUUID(),"José","12345678901","joao.silva@email.com");
        UUID id = cliente.getId();
        when(clienteGateway.buscarPorId(any())).thenReturn(Optional.of(cliente));

        // Act
        Optional<Cliente> clienteObtido = obterClientePorIdUseCaseImpl.execute(id);

        // Assert
        verify(clienteGateway, times(2)).buscarPorId(any());
        assertThat(clienteObtido).isPresent();
        assertThat(clienteObtido.get()).isInstanceOf(Cliente.class).isNotNull();
        assertThat(clienteObtido.get().getId()).isEqualTo(cliente.getId());
        assertThat(clienteObtido.get().getNome()).isEqualTo(cliente.getNome());
        assertThat(clienteObtido.get().getCpf()).isEqualTo(cliente.getCpf());
    }

}
