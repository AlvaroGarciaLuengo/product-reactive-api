package com.alvarogarcialuengo.products.application.converter;

import com.alvarogarcialuengo.products.application.dto.ProductRequest;
import com.alvarogarcialuengo.products.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRequestIntoProductConverterTest {

    private ProductRequestIntoProductConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ProductRequestIntoProductConverter();
    }

    @Test
    void testConvert() {
        ProductRequest productRequest = new ProductRequest();

        productRequest.setDescription("Test Product");
        productRequest.setPrice(new BigDecimal("19.99"));
        productRequest.setCurrency("USD");

        Product product = converter.convert(productRequest);

        assert product != null;
        assertThat(product.getDescription()).isEqualTo("Test Product");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("19.99"));
        assertThat(product.getCurrency()).isEqualTo("USD");
    }
}
