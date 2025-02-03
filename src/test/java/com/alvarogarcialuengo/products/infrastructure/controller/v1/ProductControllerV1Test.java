package com.alvarogarcialuengo.products.infrastructure.controller.v1;

import com.alvarogarcialuengo.products.application.service.v1.ProductServiceV1;
import com.alvarogarcialuengo.products.application.converter.ProductRequestIntoProductConverter;
import com.alvarogarcialuengo.products.application.dto.ProductRequest;
import com.alvarogarcialuengo.products.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

class ProductControllerV1Test {

    @Mock
    private ProductServiceV1 productService;

    @Mock
    private ProductRequestIntoProductConverter productConverter;

    @InjectMocks
    private ProductControllerV1 productControllerV1;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = bindToController(productControllerV1).build();
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product(1L, Instant.now(), null, null, null);
        Product product2 = new Product(2L, Instant.now(), null, null, null);

        when(productService.getAllProducts()).thenReturn(Flux.just(product1, product2));

        webTestClient.get().uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(2)
                .contains(product1, product2);
    }

    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product product = new Product(productId, Instant.now(), null, null, null);

        when(productService.getProductById(productId)).thenReturn(Mono.just(product));

        webTestClient.get().uri("/api/v1/products/{id}", productId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    @Test
    void testCreateProduct() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setDescription("New Product");
        productRequest.setPrice(null);
        productRequest.setCurrency("USD");

        Product product = new Product(1L, null, null, null, "USD");

        when(productConverter.convert(productRequest)).thenReturn(product);
        when(productService.createProduct(product)).thenReturn(Mono.just(product));

        webTestClient.post().uri("/api/v1/products")
                .bodyValue(productRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        ProductRequest productRequest = new ProductRequest();
        productRequest.setDescription("Updated Product");
        productRequest.setPrice(null);
        productRequest.setCurrency("USD");

        Product updatedProduct = new Product(productId, Instant.now(), null, null, "USD");

        when(productConverter.convert(productRequest)).thenReturn(updatedProduct);
        when(productService.updateProduct(productId, updatedProduct)).thenReturn(Mono.just(updatedProduct));

        webTestClient.put().uri("/api/v1/products/{id}", productId)
                .bodyValue(productRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(updatedProduct);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        when(productService.deleteProduct(productId)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/v1/products/{id}", productId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
