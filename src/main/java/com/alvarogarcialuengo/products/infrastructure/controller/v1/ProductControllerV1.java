package com.alvarogarcialuengo.products.infrastructure.controller.v1;

import com.alvarogarcialuengo.products.application.service.v1.ProductServiceV1;
import com.alvarogarcialuengo.products.application.converter.ProductRequestIntoProductConverter;
import com.alvarogarcialuengo.products.application.dto.ProductRequest;
import com.alvarogarcialuengo.products.domain.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "CRUD operations for products")
public class ProductControllerV1 {

    private final ProductServiceV1 productService;
    private final ProductRequestIntoProductConverter productConverter;

    @Autowired
    public ProductControllerV1(
            final ProductServiceV1 productService,
            final ProductRequestIntoProductConverter productConverter
    ) {
        this.productService = productService;
        this.productConverter = productConverter;
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    public Mono<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    public Mono<Product> createProduct(@RequestBody ProductRequest product) {
        return productService.createProduct(productConverter.convert(product));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public Mono<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest product) {
        return productService.updateProduct(id, productConverter.convert(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by ID")
    public Mono<ResponseEntity<Object>> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
