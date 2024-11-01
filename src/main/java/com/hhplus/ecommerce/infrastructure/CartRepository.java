package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
