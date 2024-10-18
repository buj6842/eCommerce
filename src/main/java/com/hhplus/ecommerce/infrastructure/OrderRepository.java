package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
