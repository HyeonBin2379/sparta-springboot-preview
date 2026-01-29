package com.preview.exercise.product.service;

import com.preview.exercise.product.dto.ProductCreateRequest;

public interface ProductService {

    Long saveProduct(ProductCreateRequest productCreateRequest);
}
