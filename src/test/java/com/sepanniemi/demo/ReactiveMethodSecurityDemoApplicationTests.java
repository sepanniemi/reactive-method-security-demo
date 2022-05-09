package com.sepanniemi.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;
import java.util.function.Consumer;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.Credentials.basicAuthenticationCredentials;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@SpringBootTest
class ReactiveMethodSecurityDemoApplicationTests {

	@Autowired
	ApplicationContext context;

	WebTestClient rest;

	@BeforeEach
	public void setup() {
		this.rest = WebTestClient
				.bindToApplicationContext(this.context)
				.apply(springSecurity())
				.configureClient()
				.filter(basicAuthentication())
				.build();
	}

	@Test
	void givenminorAuthenticated_whenGetBeers_thenForbidden(){
		this.rest
				.get()
				.uri("/beers")
				.attributes(minorCredentials())
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	void givenUserAuthenticated_whenGetBeers_thenAllowed(){
		this.rest
				.get()
				.uri("/beers")
				.attributes(userCredentials())
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.OK);
	}

	private Consumer<Map<String, Object>> minorCredentials() {
		return basicAuthenticationCredentials("minor", "minor");
	}

	private Consumer<Map<String, Object>> userCredentials() {
		return basicAuthenticationCredentials("user", "user");
	}

	private Consumer<Map<String, Object>> adminCredentials() {
		return basicAuthenticationCredentials("admin", "admin");
	}

}
