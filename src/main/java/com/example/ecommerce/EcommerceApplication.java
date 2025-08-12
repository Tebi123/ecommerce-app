package com.example.ecommerce;

import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EcommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(EcommerceApplication.class, args);
  }

  @GetMapping("/")
  public Map<String, String> home() {
    return Map.of("message", "Welcome to Simple E-commerce API");
  }

  @GetMapping("/api/v2")
public Map<String, Object> homeV2() {
  return Map.of(
      "message", "Welcome to E-commerce API v2.0",
      "version", "v2.0.0",
      "features", List.of("enhanced-monitoring", "version-tracking")
  );
}

  @GetMapping("/products")
  public List<Map<String, Object>> getProducts() {
    return List.of(
        Map.of("id", 1, "name", "Laptop", "price", 999.99),
        Map.of("id", 2, "name", "Phone", "price", 699.99),
        Map.of("id", 3, "name", "Headphones", "price", 199.99));
  }

  @GetMapping("/health")
  public Map<String, String> health() {
    return Map.of("status", "UP");
  }
}
