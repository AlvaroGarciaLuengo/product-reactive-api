package com.alvarogarcialuengo.products.infrastructure.repository.r2dbc;

import com.alvarogarcialuengo.products.domain.model.Currency;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CurrencyRepository  extends ReactiveCrudRepository<Currency, String> {
}
