package com.preview.exercise.product.service;

import com.preview.exercise.product.advice.exception.DuplicateProductException;
import com.preview.exercise.product.advice.exception.ProductNotFoundException;
import com.preview.exercise.product.domain.Product;
import com.preview.exercise.product.dto.ProductCreateRequest;
import com.preview.exercise.product.dto.ProductDetailResponse;
import com.preview.exercise.product.dto.ProductUpdateRequest;
import com.preview.exercise.product.repository.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicProductService implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Long saveProduct(ProductCreateRequest productCreateRequest) {
        Product newProduct = modelMapper.map(productCreateRequest, Product.class);
        try {
            Product createdProduct = productRepository.save(newProduct);
            return createdProduct.getId();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException("이미 등록된 상품이 존재합니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDetailResponse> readById(Long productId) {
        Optional<Product> foundProduct = productRepository.findById(productId);

        return foundProduct
                .map(product -> modelMapper.map(product, ProductDetailResponse.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetailResponse> readAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        return products
                .map(product -> modelMapper.map(product, ProductDetailResponse.class));
    }

    @Override
    @Transactional
    public ProductDetailResponse updateProduct(Long targetId, ProductUpdateRequest productUpdateRequest) {
        Product target = productRepository.findById(targetId)
                .orElseThrow(() -> new ProductNotFoundException("변경할 상품이 존재하지 않습니다."));
        modelMapper.map(productUpdateRequest, target);

        try {
            Product result = productRepository.save(target);
            return modelMapper.map(result, ProductDetailResponse.class);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException("이미 중복된 상품이 존재합니다.");
        }
    }

    @Override
    @Transactional
    public void deleteProduct(Long targetId) {
        Product target = productRepository.findById(targetId)
                .orElseThrow(() -> new ProductNotFoundException("삭제할 상품이 존재하지 않습니다."));
        target.setIsExisted(null);
    }
}
