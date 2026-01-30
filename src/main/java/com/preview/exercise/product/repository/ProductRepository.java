package com.preview.exercise.product.repository;

import com.preview.exercise.product.domain.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.id = :productId and p.isExisted = true")
    Optional<Product> findById(@Param("productId") Long id);

    // 조건부 update문 사용 -> 재고의 원자적 차감을 보장, 반환값은 재고 차감이 발생한 데이터 행 수(재고 차감 성공 시 1, 실패 시 0 반환)
    @Modifying
    @Query("update Product p set p.stock = p.stock - :quantity where p.id = :productId and p.stock >= :quantity")
    int decreaseStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}
