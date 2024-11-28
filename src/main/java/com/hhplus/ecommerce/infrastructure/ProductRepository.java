package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.product.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {

    Optional<Product> findById(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.productId = :productId")
    Optional<Product> findByIdWithLock(@Param("productId") Long productId);

    Product findByProductName(String productName);

    @Query("SELECT p FROM Product p WHERE p.productId IN :productIds")
    List<Product> findProductsInProductIds(@Param("productIds")List<Long> productIds);

}
