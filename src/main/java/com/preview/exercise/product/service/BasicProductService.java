package com.preview.exercise.product.service;

import com.preview.exercise.product.domain.Product;
import com.preview.exercise.product.dto.ProductInfo;
import com.preview.exercise.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicProductService implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long saveProduct(ProductInfo productInfo) {
        Product newProduct = modelMapper.map(productInfo, Product.class);

        Product createdProduct = productRepository.save(newProduct);

        return createdProduct.getId();
    }
}
