package com.example.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EcommerceIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testFullApplicationFlow() {
        // Test home endpoint
        ResponseEntity<Map> homeResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/", Map.class);
        assertThat(homeResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(homeResponse.getBody().get("message")).isEqualTo("Welcome to Simple E-commerce API");

        // Test products endpoint
        ResponseEntity<List> productsResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/products", List.class);
        assertThat(productsResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(productsResponse.getBody()).hasSize(3);

        // Test health endpoint
        ResponseEntity<Map> healthResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/health", Map.class);
        assertThat(healthResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(healthResponse.getBody().get("status")).isEqualTo("UP");
    }
}