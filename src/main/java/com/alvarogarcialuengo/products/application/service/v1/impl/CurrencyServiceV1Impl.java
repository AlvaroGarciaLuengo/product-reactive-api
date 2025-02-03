package com.alvarogarcialuengo.products.application.service.v1.impl;

import com.alvarogarcialuengo.products.application.exception.EntityNotFoundException;
import com.alvarogarcialuengo.products.application.service.v1.CurrencyServiceV1;
import com.alvarogarcialuengo.products.domain.model.Currency;
import com.alvarogarcialuengo.products.infrastructure.repository.cache.CurrencyCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyServiceV1Impl implements CurrencyServiceV1 {

    private final CurrencyCacheRepository currencyCacheRepository;

    @Autowired
    public CurrencyServiceV1Impl(final CurrencyCacheRepository currencyCacheRepository) {
        this.currencyCacheRepository = currencyCacheRepository;
    }

    @Override
    public Flux<Currency> getAllCurrencies() {
        return currencyCacheRepository.getAllCurrencies();
    }

    @Override
    public Mono<Currency> getCurrencyById(final String id) {
        return currencyCacheRepository.getCurrencyById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(Currency.class.getSimpleName())));
    }
}
