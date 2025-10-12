package br.com.fiap.cliente.usecase.cliente.impl;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.UUID;

import br.com.fiap.cliente.ClienteApplication;
import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.gateway.cliente.CognitoGateway;
import jakarta.transaction.Transactional;
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
class ObterClienteUseCaseImplIT {

    @MockBean
    private CognitoGateway cognitoGateway;

   @Autowired
    private ClienteGateway clienteGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        when(cognitoGateway.cadastrarUsuario(any(), any(), any()))
                .thenReturn(ClienteResponseDTO.builder().senha("12312").build());
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void teardown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirListarClientes(){

        //Arrange
        var listaClientes = Arrays.asList(gerarCliente(), gerarCliente());

        clienteGateway.salvar(listaClientes.get(0));

        //Act
        var retornoClientes = clienteGateway.listarTodos();

        //Assert
        assertThat(retornoClientes)
                .isNotEmpty()
                .hasSizeGreaterThan(0);

    }

    private Cliente gerarCliente(){
        return Cliente.builder().id(UUID.randomUUID()).nome("José").cpf("12345678901").build();
    }

}
