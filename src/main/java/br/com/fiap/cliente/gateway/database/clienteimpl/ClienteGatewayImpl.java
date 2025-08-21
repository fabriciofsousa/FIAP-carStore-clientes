package br.com.fiap.cliente.gateway.database.clienteimpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.fiap.cliente.gateway.database.entity.cliente.ClienteEntity;
import org.springframework.stereotype.Repository;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.gateway.database.repository.cliente.ClienteRepository;

@Repository
public class ClienteGatewayImpl implements ClienteGateway {
    private final ClienteRepository clienteRepository;

    public ClienteGatewayImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity();
        entity.setId(cliente.getId());
        entity.setNome(cliente.getNome());
        entity.setCpf(cliente.getCpf());
        ClienteEntity savedEntity = clienteRepository.save(entity);
        return new Cliente(savedEntity.getId(), savedEntity.getNome(), savedEntity.getCpf());

    }

    @Override
    public Optional<Cliente> buscarPorId(UUID id) {
        return clienteRepository.findById(id)
                .map(entity -> new Cliente(entity.getId(), entity.getNome(), entity.getCpf()));
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(entity -> new Cliente(entity.getId(), entity.getNome(), entity.getCpf()))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(UUID id) {

        clienteRepository.deleteById(id);
    }
}