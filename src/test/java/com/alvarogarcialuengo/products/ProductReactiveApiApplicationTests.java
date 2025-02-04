package com.alvarogarcialuengo.products;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.wait.strategy.Wait;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:postgresql://localhost:5432/test_db",
		"spring.datasource.username=test_user",
		"spring.datasource.password=test_password",
		"spring.redis.host=localhost",
		"spring.redis.port=6379"
})
class ProductReactiveApiApplicationTests {

	private static PostgreSQLContainer<?> postgresContainer;
	private static GenericContainer<?> redisContainer;

	@BeforeAll
	static void setup() throws Exception {
		postgresContainer = new PostgreSQLContainer<>("postgres:17")
				.withDatabaseName("test_db")
				.withUsername("test_user")
				.withPassword("test_password")
				.waitingFor(Wait.forListeningPort());

		redisContainer = new GenericContainer<>(DockerImageName.parse("redis:7.4.2"))
				.withExposedPorts(6379)
				.waitingFor(Wait.forListeningPort());

		postgresContainer.start();
		redisContainer.start();

		System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
		System.setProperty("spring.datasource.username", postgresContainer.getUsername());
		System.setProperty("spring.datasource.password", postgresContainer.getPassword());
		System.setProperty("spring.redis.host", redisContainer.getHost());
		System.setProperty("spring.redis.port", String.valueOf(redisContainer.getMappedPort(6379)));
	}

	@Test
	void contextLoads() {
		assertThat(postgresContainer.isRunning()).isTrue();
		assertThat(redisContainer.isRunning()).isTrue();
	}
}
