package com.preview.exercise.order.service;

import com.preview.exercise.order.dto.OrderCreateRequest;

public interface OrderService {

    Long createOrder(OrderCreateRequest orderCreateRequest);
}
