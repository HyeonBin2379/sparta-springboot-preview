package com.preview.exercise.order.advice;

import com.preview.exercise.common.dto.ErrorResponse;
import com.preview.exercise.order.advice.exception.OrderNotFoundException;
import com.preview.exercise.order.controller.OrderController;
import com.preview.exercise.product.advice.exception.ProductOutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = OrderController.class)
public class OrderExceptionHandler {

    @ExceptionHandler({OrderNotFoundException.class, ProductOutOfStockException.class})
    public ResponseEntity<ErrorResponse> handleOrderException(RuntimeException e) {
        HttpStatus httpStatus;

        if (e instanceof OrderNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof ProductOutOfStockException) {
            httpStatus = HttpStatus.CONFLICT;
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
