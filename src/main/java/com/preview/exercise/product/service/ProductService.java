package com.preview.exercise.product.service;

import com.preview.exercise.product.dto.ProductCreateRequest;
import com.preview.exercise.product.dto.ProductDetailResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Long saveProduct(ProductCreateRequest productCreateRequest);
    Optional<ProductDetailResponse> readById(Long productId);
    Page<ProductDetailResponse> readAll(Pageable pageable);
}
