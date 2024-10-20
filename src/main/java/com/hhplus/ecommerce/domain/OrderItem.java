package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
    private Long productId;
    private Integer quantity;
    private Integer price;

    @Builder
    public OrderItem(Long orderItemId, Order order, Long productId, Integer quantity, Integer price) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}