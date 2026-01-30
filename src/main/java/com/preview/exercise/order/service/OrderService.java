package com.preview.exercise.order.service;

import com.preview.exercise.order.dto.OrderCreateRequest;
import com.preview.exercise.order.dto.OrderDetailResponse;

public interface OrderService {

    Long createOrder(OrderCreateRequest orderCreateRequest);
    OrderDetailResponse readOrder(Long orderId);
}
