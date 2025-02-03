package com.alvarogarcialuengo.products.infrastructure.controller.v1;

import com.alvarogarcialuengo.products.application.service.v1.CurrencyServiceV1;
import com.alvarogarcialuengo.products.domain.model.Currency;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currencies")
@Tag(name = "Currencies", description = "CRUD operations for currencies.")
public class CurrencyControllerV1 {

    private final CurrencyServiceV1 currencyService;

    @Autowired
    public CurrencyControllerV1(
            final CurrencyServiceV1 currencyService
    ) {
        this.currencyService = currencyService;
    }

    @GetMapping
    @Operation(summary = "Get all currencies.")
    public Flux<Currency> getAllProducts() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a currency by its ID.")
    public Mono<Currency> getCurrencyById(@PathVariable String id) {
        return currencyService.getCurrencyById(id.toUpperCase());
    }
}
