package br.com.fiap.cliente.controller.cliente.mapper;

import br.com.fiap.cliente.controller.cliente.dto.ClienteRequestDTO;
import br.com.fiap.cliente.domain.Cliente;

public class ClienteMapper {
    public static Cliente toDomain(ClienteRequestDTO dto){
        return new Cliente(null, dto.nome(), dto.cpf(), dto.email());
    }
}
