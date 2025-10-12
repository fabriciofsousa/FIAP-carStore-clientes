package br.com.fiap.cliente.gateway.clienteimpl;

import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.cliente.ClienteGateway;
import br.com.fiap.cliente.gateway.database.entity.cliente.ClienteEntity;
import br.com.fiap.cliente.gateway.database.repository.cliente.ClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        entity.setEmail(cliente.getEmail());
        ClienteEntity savedEntity = clienteRepository.save(entity);
        return new Cliente(savedEntity.getId(), savedEntity.getNome(), savedEntity.getCpf(), savedEntity.getEmail());

    }

    @Override
    public Optional<Cliente> buscarPorId(UUID id) {
        return clienteRepository.findById(id)
                .map(entity -> new Cliente(entity.getId(), entity.getNome(), entity.getCpf(), entity.getEmail()));
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(entity -> new Cliente(entity.getId(), entity.getNome(), entity.getCpf(), entity.getEmail()))
                .toList();
    }

    @Override
    public void deletar(UUID id) {

        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        ClienteEntity entity = clienteRepository.findByEmail(email);
        if(entity == null) {
            return null;
        }
        return new Cliente(entity.getId(), entity.getNome(), entity.getCpf(), entity.getEmail());

    }
}