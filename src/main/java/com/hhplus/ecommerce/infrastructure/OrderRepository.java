package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
