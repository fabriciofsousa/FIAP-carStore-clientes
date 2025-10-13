package br.com.fiap.cliente.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @GetMapping("/alguma-rota-teste")
    public String testEndpoint() {
        return "ok";
    }
}
