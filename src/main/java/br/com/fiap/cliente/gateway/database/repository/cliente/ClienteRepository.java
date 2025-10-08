package br.com.fiap.cliente.gateway.database.repository.cliente;

import br.com.fiap.cliente.gateway.database.entity.cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

    ClienteEntity findByEmail(String email);
}
