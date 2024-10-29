package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.product.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Long> {


    Optional<Product> findById(Long productId);

    Product findByProductName(String productName);

}
