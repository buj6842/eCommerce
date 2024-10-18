package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Long> {

}
