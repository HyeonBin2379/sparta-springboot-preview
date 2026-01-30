package com.preview.exercise.order.service;

import com.preview.exercise.order.dto.OrderCreateRequest;
import com.preview.exercise.order.dto.OrderDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Long createOrder(OrderCreateRequest orderCreateRequest);
    OrderDetailResponse searchOrder(Long orderId);
    Page<OrderDetailResponse> searchAll(Pageable pageable);
}
