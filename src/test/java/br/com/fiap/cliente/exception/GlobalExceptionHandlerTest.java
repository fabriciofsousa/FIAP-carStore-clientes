package br.com.fiap.cliente.exception;

import br.com.fiap.cliente.config.GlobalExceptionHandler;
import br.com.fiap.cliente.controller.cliente.ClienteController;
import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.CognitoGateway;
import br.com.fiap.cliente.usecase.cliente.AlterarClienteUseCase;
import br.com.fiap.cliente.usecase.cliente.CriarClienteUseCase;
import br.com.fiap.cliente.usecase.cliente.DeletarClienteUseCase;
import br.com.fiap.cliente.usecase.cliente.ObterClientePorIdUseCase;
import br.com.fiap.cliente.usecase.cliente.ObterClienteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClienteController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class
        })

@TestPropertySource(properties = {
        "spring.security.oauth2.resourceserver.jwt.issuer-uri=https://cognito-idp.us-east-1.amazonaws.com/fake-pool-id",
        "cognito.user-pool-id=fake-pool-id"
})
@Import(GlobalExceptionHandler.class)
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean CriarClienteUseCase criarClienteUseCase;
    @MockBean ObterClienteUseCase obterClienteUseCase;
    @MockBean ObterClientePorIdUseCase obterClientePorIdUseCase;
    @MockBean AlterarClienteUseCase alterarClienteUseCase;
    @MockBean DeletarClienteUseCase deletarCliente;

    @MockBean CognitoGateway cognitoGateway;

    @BeforeEach
    void setUp(){
    when(cognitoGateway.cadastrarUsuario(any(), any(), any()))
            .thenReturn(ClienteResponseDTO.builder().senha("senha123").build());
    }

    @Test
    void getById_quandoNaoEncontrado_entao404_comMensagemDoHandler() throws Exception {
        UUID id = UUID.randomUUID();
        when(obterClientePorIdUseCase.execute(id))
                .thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        mockMvc.perform(get("/clientes/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado"));
    }

    @Test
    void getAll_quandoRuntimeException_entao400_comMensagemDoHandler() throws Exception {
        when(obterClienteUseCase.execute())
                .thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro inesperado"));
    }

    @Test
    void put_quandoNaoEncontrado_entao404_comMensagemDoHandler() throws Exception {
        UUID id = UUID.randomUUID();
        when(alterarClienteUseCase.execute(eq(id), any(Cliente.class)))
                .thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        // corpo mínimo só para passar pela desserialização
        mockMvc.perform(put("/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado"));
    }

    @Test
    void post_quandoRuntimeException_entao400_comMensagemDoHandler() throws Exception {
        when(criarClienteUseCase.execute(any(Cliente.class)))
                .thenThrow(new RuntimeException("Erro ao criar"));

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Teste\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro ao criar"));
    }

    @Test
    void delete_quandoNaoEncontrado_entao404_comMensagemDoHandler() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new ClienteNaoEncontradoException("Cliente não encontrado"))
                .when(deletarCliente).execute(id);

        mockMvc.perform(delete("/clientes/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado"));
    }

    @Test
    void delete_quandoSucesso_entao204() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/clientes/{id}", id))
                .andExpect(status().isNoContent());

        verify(deletarCliente).execute(id);
    }
}
