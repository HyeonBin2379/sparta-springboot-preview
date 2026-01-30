package com.preview.exercise.order.service;

import com.preview.exercise.order.domain.Order;
import com.preview.exercise.order.dto.OrderCreateRequest;
import com.preview.exercise.order.repository.OrderRepository;
import com.preview.exercise.product.advice.exception.ProductNotFoundException;
import com.preview.exercise.product.domain.Product;
import com.preview.exercise.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Long createOrder(OrderCreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("주문할 상품이 없습니다."));

        Order newOrder = Order.builder()
                .product(product)
                .quantity(request.getQuantity())
                .build();

        return orderRepository.save(newOrder).getId();
    }
}
