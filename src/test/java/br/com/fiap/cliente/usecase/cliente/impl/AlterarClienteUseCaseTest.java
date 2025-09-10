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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AlterarClienteUseCaseTest {

    private AlterarClienteUseCaseImpl alterarClienteUseCaseImpl;

    @Mock
    ClienteGateway clienteGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        alterarClienteUseCaseImpl = new AlterarClienteUseCaseImpl(clienteGateway);
    }

    @AfterEach
    void teardown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAlterarUmCliente() {
        //ARRANGE
        var cliente = Cliente.builder().id(UUID.randomUUID()).nome("José").cpf("12345678901").build();
        when(clienteGateway.buscarPorId(any())).thenReturn(Optional.of(cliente));
        when(clienteGateway.salvar(any(Cliente.class))).thenReturn(cliente);

        //ACT
        Cliente clienteObtido = alterarClienteUseCaseImpl.execute(cliente.getId(), cliente);

        //ASSERT
        verify(clienteGateway, times(1)).buscarPorId(any());
        verify(clienteGateway, times(1)).salvar(any(Cliente.class));
        assertThat(clienteObtido).isNotNull();
        assertThat(cliente.getId()).isEqualTo(clienteObtido.getId());
        assertThat(cliente.getNome()).isEqualTo(clienteObtido.getNome());
        assertThat(cliente.getCpf()).isEqualTo(clienteObtido.getCpf());

    }

    @Test
    void deveLancarExecaoQuandoClienteNaoExistir() {
        //ARRANGE PREPARAR
        UUID idInexistente = UUID.randomUUID();
        var cliente = Cliente.builder().id(UUID.randomUUID()).nome("José").cpf("12345678901").build();
        when(clienteGateway.buscarPorId(any())).thenReturn(Optional.empty());

        //ACT EXECUTAR
        assertThatThrownBy(() -> alterarClienteUseCaseImpl.execute(idInexistente, cliente))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cliente não encontrado");
        //ASSERT VERIFICAR
        verify(clienteGateway, times(1)).buscarPorId(any());
        verify(clienteGateway, never()).salvar(any(Cliente.class));
    }


}
