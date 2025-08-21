package br.com.fiap.cliente.usecase.cliente;

import br.com.fiap.cliente.domain.Cliente;

import java.util.List;

public interface ObterClienteUseCase {
    List<Cliente> execute ();
}
