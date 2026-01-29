package com.preview.exercise.product.advice;

import com.preview.exercise.common.dto.ErrorResponse;
import com.preview.exercise.product.advice.exception.DuplicateProductException;
import com.preview.exercise.product.advice.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler({DuplicateProductException.class, ProductNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleProductException(RuntimeException e) {
        HttpStatus httpStatus;

        if (e instanceof DuplicateProductException) {
            httpStatus = HttpStatus.CONFLICT;
        } else if (e instanceof ProductNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        ErrorResponse error = ErrorResponse.builder()
                .message(e.getMessage())
                .status(httpStatus.value())
                .build();

        return ResponseEntity.status(httpStatus).body(error);
    }
}
