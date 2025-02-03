package com.alvarogarcialuengo.products.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    @Schema(description = "Description of the product.", example = "Mystery Box")
    private String description;

    @Schema(description = "Price of the product.", example = "100.99")
    private BigDecimal price;

    @Schema(description = "String ISO code of the currency.", example = "EUR")
    private String currency;
}
