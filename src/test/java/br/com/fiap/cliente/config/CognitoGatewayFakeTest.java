package br.com.fiap.cliente.config;

import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.gateway.clienteimpl.CognitoGatewayFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CognitoGatewayFakeTest {

    private CognitoGatewayFake gateway;

    @BeforeEach
    void setUp() {
        gateway = new CognitoGatewayFake();
    }

    @Test
    void deveCadastrarUsuarioERetornarSenhaPadrao() {
        // Arrange
        String email = "teste@fiap.com";
        String nome = "Teste";
        ClienteResponseDTO cliente = ClienteResponseDTO.builder().build();

        // Act
        ClienteResponseDTO resultado = gateway.cadastrarUsuario(email, nome, cliente);

        // Assert
        assertNotNull(resultado, "O retorno não deve ser nulo");
        assertEquals("123456", resultado.getSenha(), "A senha padrão deve ser 123456");
    }

    @Test
    void deveExecutarAtualizacaoSemLancarExcecao() {
        assertDoesNotThrow(() ->
                gateway.atualizarUsuario("teste@fiap.com", "Novo Nome", "novoemail@fiap.com")
        );
    }
}
