package com.alvarogarcialuengo.products.infrastructure.repository.cache;

import com.alvarogarcialuengo.products.domain.model.Currency;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyCacheRepository {
    Flux<Currency> getAllCurrencies();
    Mono<Currency> getCurrencyById(String id);
}
