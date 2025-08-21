package br.com.fiap.cliente;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@EntityScan(basePackages = "br.com.fiap.cliente.gateway.database.entity.cliente")
class ClienteApplicationTests {

	@Test
	void contextLoads() {
	}

}
