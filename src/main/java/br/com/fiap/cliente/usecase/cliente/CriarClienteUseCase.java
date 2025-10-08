package br.com.fiap.cliente.usecase.cliente;

import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.domain.Cliente;

public interface CriarClienteUseCase {
    ClienteResponseDTO execute (Cliente cliente);
}
