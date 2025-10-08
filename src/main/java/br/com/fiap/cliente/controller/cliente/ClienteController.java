package br.com.fiap.cliente.controller.cliente;

import br.com.fiap.cliente.controller.cliente.dto.ClienteRequestDTO;
import br.com.fiap.cliente.controller.cliente.dto.ClienteResponseDTO;
import br.com.fiap.cliente.controller.cliente.mapper.ClienteMapper;
import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.usecase.cliente.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final CriarClienteUseCase criarClienteUseCase;
    private final ObterClienteUseCase obterClienteUseCase;
    private final ObterClientePorIdUseCase obterClientePorIdUseCase;
    private final AlterarClienteUseCase alterarClienteUseCase;
    private final DeletarClienteUseCase deletarCliente;


    public ClienteController(CriarClienteUseCase criarClienteUseCase, ObterClienteUseCase obterClienteUseCase, ObterClientePorIdUseCase obterClientePorIdUseCase, AlterarClienteUseCase alterarClienteUseCase, DeletarClienteUseCase deletarCliente)
    {
        this.criarClienteUseCase = criarClienteUseCase;
        this.obterClienteUseCase = obterClienteUseCase;
        this.obterClientePorIdUseCase = obterClientePorIdUseCase;
        this.alterarClienteUseCase = alterarClienteUseCase;
        this.deletarCliente = deletarCliente;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        var cliente = ClienteMapper.toDomain(clienteRequestDTO);

        return ResponseEntity.ok(criarClienteUseCase.execute(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cliente>> obterClientePorId(@PathVariable UUID id) {
        return ResponseEntity.ok(obterClientePorIdUseCase.execute(id));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obterCliente() {
        return ResponseEntity.ok(obterClienteUseCase.execute());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable UUID id, @RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        var cliente = ClienteMapper.toDomain(clienteRequestDTO);
        return ResponseEntity.ok(alterarClienteUseCase.execute(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable UUID id) {
        deletarCliente.execute(id);
        return ResponseEntity.noContent().build();
    }
}