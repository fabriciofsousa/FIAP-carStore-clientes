package br.com.fiap.cliente.usecase.cliente.impl;

import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.exception.ClienteException;
import br.com.fiap.cliente.gateway.clienteimpl.CognitoGatewayImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CognitoGatewayImplTest {

    private CognitoIdentityProviderClient cognitoClient;
    private CognitoGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        cognitoClient = mock(CognitoIdentityProviderClient.class);

        // criamos uma subclasse anônima para injetar o mock
        gateway = new CognitoGatewayImpl("user-pool-id", "us-east-1") {
            @Override
            public ClienteResponseDTO cadastrarUsuario(String email, String nome, ClienteResponseDTO dto) {
                // substitui o client real pelo mock para teste
                try {
                    var field = CognitoGatewayImpl.class.getDeclaredField("cognitoClient");
                    field.setAccessible(true);
                    field.set(this, cognitoClient);
                } catch (Exception ignored) {}
                return super.cadastrarUsuario(email, nome, dto);
            }

            @Override
            public void atualizarUsuario(String email, String novoNome, String novoEmail) {
                try {
                    var field = CognitoGatewayImpl.class.getDeclaredField("cognitoClient");
                    field.setAccessible(true);
                    field.set(this, cognitoClient);
                } catch (Exception ignored) {}
                super.atualizarUsuario(email, novoNome, novoEmail);
            }
        };
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        ClienteResponseDTO dto = ClienteResponseDTO.builder().build();

        // Act
        ClienteResponseDTO resultado = gateway.cadastrarUsuario("teste@fiap.com", "Nome Teste", dto);

        // Assert
        assertNotNull(resultado.getSenha(), "Deve gerar senha temporária");
        verify(cognitoClient, times(1)).adminCreateUser(any(AdminCreateUserRequest.class));
        verify(cognitoClient, times(1)).adminAddUserToGroup(any(AdminAddUserToGroupRequest.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioJaExiste() {
        doThrow(UsernameExistsException.builder().message("exists").build())
                .when(cognitoClient).adminCreateUser(any(AdminCreateUserRequest.class));

        ClienteResponseDTO dto = ClienteResponseDTO.builder().build();

        assertThrows(ClienteException.class, () ->
                gateway.cadastrarUsuario("teste@fiap.com", "Nome Teste", dto)
        );
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        assertDoesNotThrow(() ->
                gateway.atualizarUsuario("teste@fiap.com", "Novo Nome", "novoemail@fiap.com")
        );
        verify(cognitoClient, times(1)).adminUpdateUserAttributes(any(AdminUpdateUserAttributesRequest.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        doThrow(UserNotFoundException.builder().message("not found").build())
                .when(cognitoClient).adminUpdateUserAttributes(any(AdminUpdateUserAttributesRequest.class));

        assertThrows(ClienteException.class, () ->
                gateway.atualizarUsuario("teste@fiap.com", "Novo Nome", "novoemail@fiap.com")
        );
    }
}
