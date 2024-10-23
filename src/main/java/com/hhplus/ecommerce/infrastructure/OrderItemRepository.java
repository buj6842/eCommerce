package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
