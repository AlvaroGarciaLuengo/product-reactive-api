package com.alvarogarcialuengo.products.infrastructure.repository.cache.impl;

import com.alvarogarcialuengo.products.domain.model.Currency;
import com.alvarogarcialuengo.products.infrastructure.repository.cache.CurrencyCacheRepository;
import com.alvarogarcialuengo.products.infrastructure.repository.r2dbc.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CurrencyCacheRepositoryImpl implements CurrencyCacheRepository {

    private static final String CACHE_NAME = "currency:";

    private final ReactiveRedisOperations<String, Currency> redisOperations;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyCacheRepositoryImpl(
            ReactiveRedisOperations<String, Currency> redisOperations,
            CurrencyRepository currencyRepository) {
        this.redisOperations = redisOperations;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Flux<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Mono<Currency> getCurrencyById(String id) {
        String cacheKey = CACHE_NAME.concat(id);

        return redisOperations.opsForValue().get(cacheKey)
                .switchIfEmpty(
                        currencyRepository.findById(id)
                                .doOnNext(currency -> {
                                    redisOperations.opsForValue().set(cacheKey, currency).subscribe();
                                })
                );
    }

    public Mono<Void> saveCurrency(String id, Currency currency) {
        return redisOperations.opsForValue().set(id, currency)
                .then();
    }

}
