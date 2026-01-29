package com.preview.exercise.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCreateRequest {

    private String name;
    private String category;
    private Integer price;
    private Integer stock;
    private String description;
}
