package com.alvarogarcialuengo.products.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyTest {

    private Currency currency;

    @BeforeEach
    void setUp() {
        currency = new Currency("USD", "$");
    }

    @Test
    void testCurrencyCreation() {
        assertThat(currency.getId()).isEqualTo("USD");
        assertThat(currency.getSymbol()).isEqualTo("$");
    }

    @Test
    void testCurrencySettersAndGetters() {
        currency.setId("EUR");
        currency.setSymbol("€");

        assertThat(currency.getId()).isEqualTo("EUR");
        assertThat(currency.getSymbol()).isEqualTo("€");
    }
}
