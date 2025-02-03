package com.alvarogarcialuengo.products.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Test Product", new BigDecimal("19.99"), "USD");
    }

    @Test
    void testProductCreation() {
        assertThat(product.getDescription()).isEqualTo("Test Product");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("19.99"));
        assertThat(product.getCurrency()).isEqualTo("USD");
    }

    @Test
    void testProductIdAndCreationDateAreNullInitially() {
        assertThat(product.getId()).isNull();
        assertThat(product.getCreationDate()).isNull();
    }

    @Test
    void testProductSettersAndGetters() {
        product.setId(1L);
        product.setCreationDate(Instant.now());

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getCreationDate()).isNotNull();
    }

    @Test
    void testProductConstructorWithDescriptionPriceAndCurrency() {
        Product newProduct = new Product("New Product", new BigDecimal("49.99"), "EUR");

        assertThat(newProduct.getDescription()).isEqualTo("New Product");
        assertThat(newProduct.getPrice()).isEqualTo(new BigDecimal("49.99"));
        assertThat(newProduct.getCurrency()).isEqualTo("EUR");
    }
}
