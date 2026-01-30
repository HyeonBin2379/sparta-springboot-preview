package com.preview.exercise.product.advice.exception;

public class ProductConflictException extends RuntimeException {
  public ProductConflictException(String message) {
    super(message);
  }
}
