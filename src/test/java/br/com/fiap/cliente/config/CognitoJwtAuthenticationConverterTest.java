package br.com.fiap.cliente.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CognitoJwtAuthenticationConverterTest {

    private CognitoJwtAuthenticationConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CognitoJwtAuthenticationConverter();
    }

    @Test
    void deveConverterJwtComGrupos() {
        // Arrange
        Jwt jwt = new Jwt(
                "token-value",
                Instant.now(),
                Instant.now().plusSeconds(3600),
                Map.of("alg", "none"),
                Map.of("cognito:groups", List.of("admin", "user"))
        );

        // Act
        JwtAuthenticationToken authToken = (JwtAuthenticationToken) converter.convert(jwt);

        // Assert
        assertNotNull(authToken);
        assertEquals(2, authToken.getAuthorities().size());
        assertTrue(authToken.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN")));
        assertTrue(authToken.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void deveRetornarVazioQuandoNaoHaGrupos() {
        // Arrange
        Jwt jwt = new Jwt(
                "token-value",
                Instant.now(),
                Instant.now().plusSeconds(3600),
                Map.of("alg", "none"),
                Map.of( "cognito:groups", List.of())
        );

        // Act
        JwtAuthenticationToken authToken = (JwtAuthenticationToken) converter.convert(jwt);

        // Assert
        assertNotNull(authToken);
        assertTrue(authToken.getAuthorities().isEmpty());
    }
}