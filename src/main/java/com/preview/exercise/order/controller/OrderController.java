package com.preview.exercise.order.controller;

import com.preview.exercise.order.dto.OrderCreateRequest;
import com.preview.exercise.order.dto.OrderDetailResponse;
import com.preview.exercise.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Long> create(@RequestBody OrderCreateRequest request) {
        Long orderId = orderService.createOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDetailResponse> search(@PathVariable(name = "id") Long orderId) {
        OrderDetailResponse response = orderService.searchOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
