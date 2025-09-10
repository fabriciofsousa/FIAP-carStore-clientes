package br.com.fiap.cliente.gateway.database.entity.cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String nome;
    private String cpf;
    private String email;

}
