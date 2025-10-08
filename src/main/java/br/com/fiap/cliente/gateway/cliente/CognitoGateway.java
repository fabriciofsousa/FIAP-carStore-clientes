package br.com.fiap.cliente.gateway.cliente;

import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;

public interface CognitoGateway {

    public ClienteResponseDTO cadastrarUsuario(String email, String nome, ClienteResponseDTO clienteResponseDTO);
}
