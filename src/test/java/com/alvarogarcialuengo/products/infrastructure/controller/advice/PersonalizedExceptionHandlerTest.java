package com.alvarogarcialuengo.products.infrastructure.controller.advice;

import com.alvarogarcialuengo.products.application.exception.EntityNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersonalizedExceptionHandlerTest {

    @InjectMocks
    private PersonalizedExceptionHandler personalizedExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleDataIntegrityViolationException_withCurrencyError() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Integrity violation: products_currency_id");

        ResponseEntity<Object> response = personalizedExceptionHandler.handleDataIntegrityViolation(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorMessage errorMessage = (ErrorMessage) response.getBody();
        assertNotNull(errorMessage);
        assertEquals("The currency doesn't exist.", errorMessage.getMessage());
    }

    @Test
    void testHandleDataIntegrityViolationException_withoutCurrencyError() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Some other integrity violation");

        ResponseEntity<Object> response = personalizedExceptionHandler.handleDataIntegrityViolation(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorMessage errorMessage = (ErrorMessage) response.getBody();
        assertNotNull(errorMessage);
        assertEquals("Internal Server Error, please try again later.", errorMessage.getMessage());
    }

    @Test
    void testHandleEntityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("Product");

        ResponseEntity<Object> response = personalizedExceptionHandler.handleEntityNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorMessage errorMessage = (ErrorMessage) response.getBody();
        assertNotNull(errorMessage);
        assertEquals("Not found entity Product", errorMessage.getMessage());
    }
}
