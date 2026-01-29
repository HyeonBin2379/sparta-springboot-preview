package com.preview.exercise.product.service;

import com.preview.exercise.product.dto.ProductCreateRequest;
import com.preview.exercise.product.dto.ProductDetailResponse;
import java.util.Optional;

public interface ProductService {

    Long saveProduct(ProductCreateRequest productCreateRequest);
    Optional<ProductDetailResponse> readProductById(Long productId);
}
