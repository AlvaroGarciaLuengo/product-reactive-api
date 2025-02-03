package com.alvarogarcialuengo.products.application.service.v1;


import com.alvarogarcialuengo.products.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductServiceV1 {
    Flux<Product> getAllProducts();
    Mono<Product> getProductById(Long id);
    Mono<Product> createProduct(Product product);
    Mono<Product> updateProduct(Long id, Product product);
    Mono<Void> deleteProduct(Long id);
}