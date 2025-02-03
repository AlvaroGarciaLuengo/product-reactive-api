package com.alvarogarcialuengo.products.application.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRequestTest {

    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest();
        productRequest.setDescription("Mystery Box");
        productRequest.setPrice(new BigDecimal("100.99"));
        productRequest.setCurrency("EUR");
    }

    @Test
    void testProductRequestGettersAndSetters() {
        assertThat(productRequest.getDescription()).isEqualTo("Mystery Box");
        assertThat(productRequest.getPrice()).isEqualTo(new BigDecimal("100.99"));
        assertThat(productRequest.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void testProductRequestSetters() {
        productRequest.setDescription("New Product");
        productRequest.setPrice(new BigDecimal("49.99"));
        productRequest.setCurrency("USD");

        assertThat(productRequest.getDescription()).isEqualTo("New Product");
        assertThat(productRequest.getPrice()).isEqualTo(new BigDecimal("49.99"));
        assertThat(productRequest.getCurrency()).isEqualTo("USD");
    }
}
