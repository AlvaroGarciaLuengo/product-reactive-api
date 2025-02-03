package com.alvarogarcialuengo.products.application.service.v1.impl;

import com.alvarogarcialuengo.products.application.service.v1.ProductServiceV1;
import com.alvarogarcialuengo.products.domain.model.Product;
import com.alvarogarcialuengo.products.infrastructure.repository.r2dbc.ProductRepository;
import com.alvarogarcialuengo.products.application.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class ProductServiceV1Impl implements ProductServiceV1 {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceV1Impl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll()
                .switchIfEmpty(Mono.error(new EntityNotFoundException(Product.class.getSimpleName())));
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id.toString())
                .switchIfEmpty(Mono.error(new EntityNotFoundException(Product.class.getSimpleName())));
    }

    public Mono<Product> createProduct(Product product) {
        product.setCreationDate(Instant.now());
        return productRepository.save(product);
    }

    public Mono<Product> updateProduct(Long id, Product product) {
        return productRepository.findById(String.valueOf(id))
                .flatMap(existingProduct -> {
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setCurrency(product.getCurrency());
                    return productRepository.save(existingProduct);
                })
                .switchIfEmpty(Mono.error(new EntityNotFoundException(Product.class.getSimpleName())));
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productRepository.findById(String.valueOf(id))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(Product.class.getSimpleName())))
                .then(productRepository.deleteById(String.valueOf(id)));
    }

}
