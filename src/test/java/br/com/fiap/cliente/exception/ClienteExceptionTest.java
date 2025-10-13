package br.com.fiap.cliente.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteExceptionTest {

    @Test
    void deveConterMensagemInformada() {
        String mensagem = "Erro ao processar cliente";
        ClienteException exception = new ClienteException(mensagem);

        assertEquals(mensagem, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void devePermitirMensagemNula() {
        ClienteException exception = new ClienteException(null);
        assertNull(exception.getMessage(), "Mensagem deve ser nula quando passada nula");
    }
}
