package com.alvarogarcialuengo.products.infrastructure.repository.r2dbc;

import com.alvarogarcialuengo.products.domain.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.Instant;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {
    @Query("SELECT * FROM products WHERE creation_date BETWEEN :startDate AND :endDate")
    Flux<Product> findByCreationDateBetween(Instant startDate, Instant endDate);
}
