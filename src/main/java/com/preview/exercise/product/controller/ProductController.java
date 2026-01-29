package com.preview.exercise.product.controller;

import com.preview.exercise.product.dto.ProductCreateRequest;
import com.preview.exercise.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Long> addProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        Long newProductId = productService.saveProduct(productCreateRequest);

        if (newProductId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newProductId);
    }
}
