package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class DeletarClienteUseCaseImplTest {

    DeletarClienteUseCaseImpl deletarClienteUseCaseImpl;

    @Mock
    ClienteGateway clienteGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        deletarClienteUseCaseImpl = new DeletarClienteUseCaseImpl(clienteGateway);
    }

    @AfterEach
    void teardown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirDeletarUmClientePeloId() {
        // Arrange
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente(id,"José","12345678901");
        UUID idCliente = cliente.getId();
        when(clienteGateway.buscarPorId(idCliente)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteGateway).deletar(idCliente);



        // Act
        deletarClienteUseCaseImpl.execute(idCliente);

        // Assert
        verify(clienteGateway, times(1)).deletar(any());
    }

    @Test
    void deveGerarExceptionAoDeletarUmCliente_Cliente_Nao_Existe() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();
        when(clienteGateway.buscarPorId(any())).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThatThrownBy(() -> deletarClienteUseCaseImpl.execute(idInexistente))
                .isInstanceOf(ClienteNaoEncontradoException.class)
                .hasMessage("Cliente não encontrado");
        verify(clienteGateway, times(1)).buscarPorId(any());
        verify(clienteGateway, never()).deletar(any());

    }


    @Test
    void deveGerarExceptionQuandoIdForNulo() {
        // Act & Assert
        assertThatThrownBy(() -> deletarClienteUseCaseImpl.execute(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ID cliente não pode ser nulo");
    }


}
