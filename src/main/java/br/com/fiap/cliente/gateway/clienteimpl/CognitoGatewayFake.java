package br.com.fiap.cliente.gateway.clienteimpl;

import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.gateway.cliente.CognitoGateway;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class CognitoGatewayFake implements CognitoGateway {
    @Override
    public ClienteResponseDTO cadastrarUsuario(String email, String nome, ClienteResponseDTO clienteResponseDTO) {
        return null;
    }

    @Override
    public void atualizarUsuario(String email, String novoNome, String novoEmail) {

    }
}
