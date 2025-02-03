package com.alvarogarcialuengo.products.application.exception;

public class EntityNotFoundException extends RuntimeException {
    private final static String NOT_FOUND = "Not found entity ";
    public EntityNotFoundException(String entity) {
        super(NOT_FOUND.concat(entity));
    }
}
