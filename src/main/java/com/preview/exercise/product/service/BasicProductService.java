package com.preview.exercise.product.service;

import com.preview.exercise.product.advice.exception.DuplicateProductException;
import com.preview.exercise.product.domain.Product;
import com.preview.exercise.product.dto.ProductInfo;
import com.preview.exercise.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicProductService implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Long saveProduct(ProductInfo productInfo) {
        Product newProduct = modelMapper.map(productInfo, Product.class);
        try {
            Product createdProduct = productRepository.save(newProduct);
            return createdProduct.getId();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException("이미 등록된 상품이 존재합니다.");
        }
    }
}
