package com.hhplus.ecommerce.domain.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime orderDate;

    @Builder
    public OrderItem(Long orderItemId, Long orderId, Long productId, Integer quantity, LocalDateTime orderDate) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }
}