package com.VilmarPrestes.order_microservice;

import com.VilmarPrestes.order_microservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderMicroserviceApplicationTests {
@Autowired
private WebTestClient webTestClient;

	@Test
	void testCreateOrderSucess() {
		var user = new User(
//				"Maria Silva",
//				"maria.silva@email.com"

			);

		webTestClient
				.post()
				.uri("/users")
				.bodyValue(user)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$.id").isNumber()  // ID deve ser um número gerado automaticamente
				.jsonPath("$.name").isEqualTo(user.getName()) // Verifica o nome correto
				.jsonPath("$.email").isEqualTo(user.getEmail()); // Verifica o email correto
	}

	@Test
	void testCreateOrderFailure() {
//		var user = new User("", "");

		webTestClient
				.post()
				.uri("/users")
//				.bodyValue(user)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody();
	}
}
