package com.alvarogarcialuengo.products.application.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntityNotFoundExceptionTest {

    @Test
    void testEntityNotFoundExceptionMessage() {
        String entity = "Product";
        EntityNotFoundException exception = new EntityNotFoundException(entity);

        assertThat(exception.getMessage()).isEqualTo("Not found entity Product");
    }
}
