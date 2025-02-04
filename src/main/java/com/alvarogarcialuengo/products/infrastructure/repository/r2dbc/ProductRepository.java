package com.alvarogarcialuengo.products.infrastructure.repository.r2dbc;

import com.alvarogarcialuengo.products.domain.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {
}
