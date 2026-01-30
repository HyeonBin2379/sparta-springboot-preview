package com.preview.exercise.order.service;

import com.preview.exercise.order.advice.exception.OrderNotFoundException;
import com.preview.exercise.order.domain.Order;
import com.preview.exercise.order.dto.OrderCreateRequest;
import com.preview.exercise.order.dto.OrderDetailResponse;
import com.preview.exercise.order.repository.OrderRepository;
import com.preview.exercise.product.advice.exception.ProductNotFoundException;
import com.preview.exercise.product.advice.exception.ProductOutOfStockException;
import com.preview.exercise.product.domain.Product;
import com.preview.exercise.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        // 재고가 부족하면 상품 재고 데이터에 변동 없음 -> 주문 생성 불가
        int affected = productRepository.decreaseStock(request.getProductId(), request.getQuantity());
        if (affected == 0) {
            throw new ProductOutOfStockException("상품의 재고가 부족합니다.");
        }

        Order newOrder = Order.builder()
                .product(product)
                .quantity(request.getQuantity())
                .build();

        return orderRepository.save(newOrder).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailResponse searchOrder(Long orderId) {
        // 주문 내역 검색
        Order foundOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문 정보를 찾을 수 없습니다."));
        return convertToResponse(foundOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDetailResponse> searchAll(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("주문 목록을 조회할 수 없습니다.");
        }

        return orders.map(this::convertToResponse);
    }

    private OrderDetailResponse convertToResponse(Order order) {
        Product product = order.getProduct();

        return OrderDetailResponse.builder()
                .orderId(order.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .quantity(order.getQuantity())
                .totalPrice(product.getPrice() * order.getQuantity())
                .orderDate(order.getOrderDate())
                .build();
    }
}
