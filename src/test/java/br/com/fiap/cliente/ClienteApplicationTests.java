package br.com.fiap.cliente;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;

@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@EntityScan(basePackages = "br.com.fiap.cliente.gateway.database.entity.cliente")
class ClienteApplicationTests {

	@Test
	void contextLoads() {
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
			em.setJpaPropertyMap(properties);

			return em;
		}
	}
}
