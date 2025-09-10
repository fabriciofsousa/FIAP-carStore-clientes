package br.com.fiap.cliente.controller.cliente;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

import br.com.fiap.cliente.ClienteApplication;
import br.com.fiap.cliente.controller.cliente.dto.ClienteRequestDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.UUID;

@SpringBootTest(
        classes = ClienteApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class ClienteControllerIT {

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context).build());
    }

    @Nested
    class CriarCliente {
        @Test
        void deveCriarUmCliente() {
            ClienteRequestDTO cliente = new ClienteRequestDTO(
                    "João Silva",
                    "07406565940",
                    "joao.silva@email.com"
            );

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(cliente)
                    .when()
                    .post("/clientes")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .and()
                    .body("nome", equalTo("João Silva"))
                    .body("cpf", equalTo("07406565940"));
        }
    }

    @Configuration
    static class H2TestConfig {

        @Bean
        @Primary
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
            dataSource.setUsername("sa");
            dataSource.setPassword("");
            return dataSource;
        }

        @Bean
        @Primary
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource);
            em.setPackagesToScan("br.com.fiap.cliente.gateway.database.entity.cliente");
            em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("hibernate.hbm2ddl.auto", "create-drop");
            properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            properties.put("hibernate.show_sql", "true");
            em.setJpaPropertyMap(properties);

            return em;
        }
    }
}
