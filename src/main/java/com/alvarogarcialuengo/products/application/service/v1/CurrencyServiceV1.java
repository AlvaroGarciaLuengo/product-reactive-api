package com.alvarogarcialuengo.products.application.service.v1;

import com.alvarogarcialuengo.products.domain.model.Currency;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyServiceV1 {
    Flux<Currency> getAllCurrencies();
    Mono<Currency> getCurrencyById(String id);
}
