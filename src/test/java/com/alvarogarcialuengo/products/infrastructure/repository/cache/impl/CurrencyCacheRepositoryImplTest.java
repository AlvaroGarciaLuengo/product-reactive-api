package com.alvarogarcialuengo.products.infrastructure.repository.cache.impl;

import com.alvarogarcialuengo.products.domain.model.Currency;
import com.alvarogarcialuengo.products.infrastructure.repository.r2dbc.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class CurrencyCacheRepositoryImplTest {

    @Mock
    private ReactiveRedisOperations<String, Currency> redisOperations;

    @Mock
    private ReactiveValueOperations<String, Currency> valueOperations;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyCacheRepositoryImpl currencyCacheRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisOperations.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testGetCurrencyById_whenCurrencyInCache() {
        Currency currency = new Currency("USD", "$");
        String cacheKey = "currency:USD";

        when(valueOperations.get(cacheKey)).thenReturn(Mono.just(currency));
        when(currencyRepository.findById(currency.getId())).thenReturn(Mono.just(currency));

        StepVerifier.create(currencyCacheRepository.getCurrencyById("USD"))
                .expectNext(currency)
                .verifyComplete();

        verify(valueOperations, times(1)).get(cacheKey);
    }

    @Test
    void testGetCurrencyById_whenCurrencyNotInCache() {
        Currency currency = new Currency("EUR", "€");
        String cacheKey = "currency:EUR";

        when(valueOperations.get(cacheKey)).thenReturn(Mono.empty());
        when(currencyRepository.findById("EUR")).thenReturn(Mono.just(currency));
        when(valueOperations.set(cacheKey, currency)).thenReturn(Mono.empty());

        StepVerifier.create(currencyCacheRepository.getCurrencyById("EUR"))
                .expectNext(currency)
                .verifyComplete();

        verify(valueOperations, times(1)).get(cacheKey);
        verify(valueOperations, times(1)).set(cacheKey, currency);
        verify(currencyRepository, times(1)).findById("EUR");
    }

    @Test
    void testSaveCurrency() {
        Currency currency = new Currency("GBP", "£");
        String cacheKey = "currency:GBP";

        when(valueOperations.set(cacheKey, currency)).thenReturn(Mono.empty());

        StepVerifier.create(currencyCacheRepository.saveCurrency(cacheKey, currency))
                .verifyComplete();

        verify(valueOperations, times(1)).set(cacheKey, currency);
    }

    @Test
    void testGetAllCurrencies() {
        Currency currency1 = new Currency("USD", "$");
        Currency currency2 = new Currency("EUR", "€");

        when(currencyRepository.findAll()).thenReturn(Flux.just(currency1, currency2));

        StepVerifier.create(currencyCacheRepository.getAllCurrencies())
                .expectNext(currency1)
                .expectNext(currency2)
                .verifyComplete();

        verify(currencyRepository, times(1)).findAll();
    }
}
