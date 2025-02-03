package com.alvarogarcialuengo.products.application.service.v1.impl;

import com.alvarogarcialuengo.products.application.exception.EntityNotFoundException;
import com.alvarogarcialuengo.products.domain.model.Product;
import com.alvarogarcialuengo.products.infrastructure.repository.r2dbc.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.Mockito.*;

class ProductServiceV1ImplTest {

    private ProductRepository productRepository;
    private ProductServiceV1Impl productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceV1Impl(productRepository);
    }

    @Test
    void testGetAllProducts_whenProductsExist() {
        Product product1 = new Product(1L, Instant.now(), "Product 1", new BigDecimal("99.99"), "USD");
        Product product2 = new Product(2L, Instant.now(), "Product 2", new BigDecimal("149.99"), "EUR");

        when(productRepository.findAll()).thenReturn(Flux.just(product1, product2));

        StepVerifier.create(productService.getAllProducts())
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }

    @Test
    void testGetAllProducts_whenNoProductsExist() {
        when(productRepository.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(productService.getAllProducts())
                .expectError(EntityNotFoundException.class)
                .verify();
    }

    @Test
    void testGetProductById_whenProductExists() {
        Product product = new Product(1L, Instant.now(), "Test Product", new BigDecimal("19.99"), "USD");

        when(productRepository.findById("1")).thenReturn(Mono.just(product));

        StepVerifier.create(productService.getProductById(1L))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testGetProductById_whenProductDoesNotExist() {
        when(productRepository.findById("999")).thenReturn(Mono.empty());

        StepVerifier.create(productService.getProductById(999L))
                .expectError(EntityNotFoundException.class)
                .verify();
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(null, null, "New Product", new BigDecimal("29.99"), "USD");
        Product savedProduct = new Product(1L, Instant.now(), "New Product", new BigDecimal("29.99"), "USD");

        when(productRepository.save(product)).thenReturn(Mono.just(savedProduct));

        StepVerifier.create(productService.createProduct(product))
                .expectNext(savedProduct)
                .verifyComplete();
    }

    @Test
    void testUpdateProduct_whenProductExists() {
        Product existingProduct = new Product(1L, Instant.now(), "Old Product", new BigDecimal("19.99"), "USD");
        Product updatedProduct = new Product(1L, Instant.now(), "Updated Product", new BigDecimal("29.99"), "USD");

        when(productRepository.findById("1")).thenReturn(Mono.just(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(productService.updateProduct(1L, updatedProduct))
                .expectNext(updatedProduct)
                .verifyComplete();
    }

    @Test
    void testUpdateProduct_whenProductDoesNotExist() {
        Product product = new Product(999L, Instant.now(), "Nonexistent Product", new BigDecimal("199.99"), "USD");

        when(productRepository.findById("999")).thenReturn(Mono.empty());

        StepVerifier.create(productService.updateProduct(999L, product))
                .expectError(EntityNotFoundException.class)
                .verify();
    }

    @Test
    void testDeleteProduct_whenProductExists() {
        Product product = new Product(1L, Instant.now(), "Test Product", new BigDecimal("19.99"), "USD");

        when(productRepository.findById("1")).thenReturn(Mono.just(product));
        when(productRepository.deleteById("1")).thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(1L))
                .verifyComplete();
    }

    @Test
    void testDeleteProduct_whenProductDoesNotExist() {
        when(productRepository.findById("999")).thenReturn(Mono.empty());
        when(productRepository.deleteById("999")).thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(999L))
                .expectError(EntityNotFoundException.class)
                .verify();
    }
}
