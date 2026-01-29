package com.preview.exercise.product.advice.exception;

public class DuplicateProductException extends RuntimeException {

    public DuplicateProductException(String message) {
        super(message);
    }
}
