package com.alvarogarcialuengo.products.application.service.v1.impl;

import com.alvarogarcialuengo.products.application.exception.EntityNotFoundException;
import com.alvarogarcialuengo.products.domain.model.Currency;
import com.alvarogarcialuengo.products.infrastructure.repository.cache.CurrencyCacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CurrencyServiceV1ImplTest {

    private CurrencyCacheRepository currencyCacheRepository;
    private CurrencyServiceV1Impl currencyService;

    @BeforeEach
    void setUp() {
        currencyCacheRepository = mock(CurrencyCacheRepository.class);
        currencyService = new CurrencyServiceV1Impl(currencyCacheRepository);
    }

    @Test
    void testGetCurrencyById_whenCurrencyExists() {
        Currency currency = new Currency("USD", "$");
        when(currencyCacheRepository.getCurrencyById("USD")).thenReturn(Mono.just(currency));

        StepVerifier.create(currencyService.getCurrencyById("USD"))
                .expectNext(currency)
                .verifyComplete();
    }

    @Test
    void testGetCurrencyById_whenCurrencyDoesNotExist() {
        when(currencyCacheRepository.getCurrencyById("EUR")).thenReturn(Mono.empty());

        StepVerifier.create(currencyService.getCurrencyById("EUR"))
                .expectError(EntityNotFoundException.class)
                .verify();
    }

    @Test
    void testGetAllCurrencies() {
        Currency currency1 = new Currency("USD", "$");
        Currency currency2 = new Currency("EUR", "€");
        when(currencyCacheRepository.getAllCurrencies()).thenReturn(Flux.just(currency1, currency2));

        StepVerifier.create(currencyService.getAllCurrencies())
                .expectNext(currency1)
                .expectNext(currency2)
                .verifyComplete();
    }
}
