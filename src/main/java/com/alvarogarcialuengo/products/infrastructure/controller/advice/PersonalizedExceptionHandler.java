package com.alvarogarcialuengo.products.infrastructure.controller.advice;

import com.alvarogarcialuengo.products.application.exception.EntityNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class PersonalizedExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(final DataIntegrityViolationException ex) {
        String error = "Internal Server Error, please try again later.";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if(ex.getMessage().contains("products_currency_id")) {
            error = "The currency doesn't exist.";
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(
                new ErrorMessage(error),
                httpStatus
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(final EntityNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
