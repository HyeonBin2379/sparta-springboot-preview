package com.preview.exercise.product.repository;

import com.preview.exercise.product.domain.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.id = :productId and p.isDeleted = false")
    Optional<Product> findProductById(@Param("productId") Long id);
}
