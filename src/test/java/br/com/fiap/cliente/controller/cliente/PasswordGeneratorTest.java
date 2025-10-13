package br.com.fiap.cliente.controller.cliente;

import br.com.fiap.cliente.controller.cliente.util.PasswordGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    @Test
    void deveGerarSenhaComTamanhoCorreto() {
        String senha = PasswordGenerator.generateTemporaryPassword();
        assertEquals(12, senha.length(), "A senha deve ter 12 caracteres");
    }

    @Test
    void deveConterPeloMenosUmCaractereDeCadaTipo() {
        String senha = PasswordGenerator.generateTemporaryPassword();

        boolean temMaiuscula = senha.chars().anyMatch(Character::isUpperCase);
        boolean temMinuscula = senha.chars().anyMatch(Character::isLowerCase);
        boolean temDigito = senha.chars().anyMatch(Character::isDigit);
        boolean temSimbolo = senha.chars().anyMatch(c ->
                "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(c) >= 0
        );

        assertTrue(temMaiuscula, "Deve conter ao menos uma letra maiúscula");
        assertTrue(temMinuscula, "Deve conter ao menos uma letra minúscula");
        assertTrue(temDigito, "Deve conter ao menos um número");
        assertTrue(temSimbolo, "Deve conter ao menos um símbolo");
    }

    @RepeatedTest(10)
    void deveGerarSenhasDiferentesEmChamadasDistintas() {
        String senha1 = PasswordGenerator.generateTemporaryPassword();
        String senha2 = PasswordGenerator.generateTemporaryPassword();
        assertNotEquals(senha1, senha2, "Senhas consecutivas não devem ser iguais");
    }
}
