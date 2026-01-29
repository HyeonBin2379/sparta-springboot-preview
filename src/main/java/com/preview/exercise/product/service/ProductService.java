package com.preview.exercise.product.service;

import com.preview.exercise.product.domain.Product;
import com.preview.exercise.product.dto.ProductInfo;
import java.util.List;

public interface ProductService {

    Long saveProduct(ProductInfo productInfo);
}
