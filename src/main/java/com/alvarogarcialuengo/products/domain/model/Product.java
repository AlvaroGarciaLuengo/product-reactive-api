package com.alvarogarcialuengo.products.domain.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("products")
public class Product {
    @Id
    @Schema(requiredMode = NOT_REQUIRED, description = "Autogenerated ID of the product.")
    private Long id;

    @Schema(requiredMode = NOT_REQUIRED, description = "Autogenerated creation date of the product.")
    private Instant creationDate;
    private String description;
    private BigDecimal price;
    @Column("currency_id")
    private String currency;

    public Product(final String description, final BigDecimal price, final String currency) {
        this.description = description;
        this.price = price;
        this.currency = currency;
    }
}
