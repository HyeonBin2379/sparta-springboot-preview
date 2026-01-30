package com.preview.exercise.order.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

    private Long orderId;
    private String productName;
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;
    private LocalDateTime orderDate;
}
