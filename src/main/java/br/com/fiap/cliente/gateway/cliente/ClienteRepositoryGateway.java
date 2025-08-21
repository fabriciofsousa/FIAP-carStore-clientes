package br.com.fiap.cliente.gateway.cliente;

import br.com.fiap.cliente.domain.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepositoryGateway {
    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorId(UUID id);

    List<Cliente> listarTodos();

    void deletar(UUID id);

}
