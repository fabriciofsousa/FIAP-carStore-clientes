package br.com.fiap.cliente.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev") // força o perfil "dev"
class DevSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void devePermitirAcessoSemAutenticacaoNoPerfilDev() throws Exception {
        mockMvc.perform(get("/alguma-rota-teste"))
                .andExpect(status().isOk()); // espera 200 (permitAll)
    }
}

