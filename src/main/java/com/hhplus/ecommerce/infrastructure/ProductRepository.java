package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {

}
