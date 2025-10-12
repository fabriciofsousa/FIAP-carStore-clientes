package br.com.fiap.cliente.controller.cliente.dto;

import br.com.fiap.cliente.domain.Cliente;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponseDTO {
    private String id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;

    public static ClienteResponseDTO mapper (Cliente cliente, String senhaTemporaria){
        return new ClienteResponseDTO(
                cliente.getId().toString(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                senhaTemporaria
        );
    }
}
