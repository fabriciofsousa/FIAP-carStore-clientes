package br.com.fiap.cliente.usecase.cliente.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.cliente.ClienteApplication;
import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import jakarta.transaction.Transactional;

import java.util.UUID;

@SpringBootTest(classes = ClienteApplication.class,
        properties = {"spring.main.lazy-initialization=true"})
@EnableAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class,
        OAuth2ResourceServerAutoConfiguration.class
})
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@Transactional
class CriarClienteUseCaseImplIT {

    @Autowired
    private ClienteGateway clienteGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);

    }

    @AfterEach
    void teardown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarTabela(){

        var totalRegistros = (long) clienteGateway.listarTodos().size();

        assertThat(totalRegistros).isNotNegative();

    }
    @Test
    void devePermitirCadastrarCliente(){
        //Arrange
        var cliente = gerarCliente();
        //Act
        var clienteSalvo = clienteGateway.salvar(cliente);
        //Assert
        assertThat(clienteSalvo)
                .isInstanceOf(Cliente.class)
                .isNotNull();
        assertThat(clienteSalvo.getId()).isNotNull();

    }

    private Cliente gerarCliente(){
        return Cliente.builder().id(UUID.randomUUID()).nome("José").cpf("12345678901").build();
    }

}
