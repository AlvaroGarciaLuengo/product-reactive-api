package com.alvarogarcialuengo.products.infrastructure.controller.v1;

import com.alvarogarcialuengo.products.application.service.v1.CurrencyServiceV1;
import com.alvarogarcialuengo.products.domain.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

class CurrencyControllerV1Test {

    @Mock
    private CurrencyServiceV1 currencyService;

    @InjectMocks
    private CurrencyControllerV1 currencyControllerV1;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = bindToController(currencyControllerV1).build();
    }

    @Test
    void testGetAllCurrencies() {
        Currency currency1 = new Currency("USD", "$");
        Currency currency2 = new Currency("EUR", "€");

        when(currencyService.getAllCurrencies()).thenReturn(Flux.just(currency1, currency2));

        webTestClient.get().uri("/api/v1/currencies")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Currency.class)
                .hasSize(2)
                .contains(currency1, currency2);
    }

    @Test
    void testGetCurrencyById() {
        String currencyId = "USD";
        Currency currency = new Currency(currencyId, "$");

        when(currencyService.getCurrencyById(currencyId)).thenReturn(Mono.just(currency));

        webTestClient.get().uri("/api/v1/currencies/{id}", currencyId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Currency.class)
                .isEqualTo(currency);
    }
}
