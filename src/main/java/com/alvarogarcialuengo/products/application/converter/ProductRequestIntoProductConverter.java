package com.alvarogarcialuengo.products.application.converter;

import com.alvarogarcialuengo.products.application.dto.ProductRequest;
import com.alvarogarcialuengo.products.domain.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestIntoProductConverter implements Converter<ProductRequest, Product> {

    @Override
    public Product convert(ProductRequest productRequest) {
        return new Product(
                productRequest.getDescription().strip(),
                productRequest.getPrice(),
                productRequest.getCurrency().toUpperCase()
        );
    }
}
