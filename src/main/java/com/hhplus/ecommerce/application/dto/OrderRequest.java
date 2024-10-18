package com.hhplus.ecommerce.application.dto;

import java.util.List;

public record OrderRequest(
        Long userId,
        List<OrderItemDTO> orderItems,
        Integer totalPrice
) {
    public OrderRequest(Long userId, List<OrderItemDTO> orderItems) {
        this(userId, orderItems, calculateTotalPrice(orderItems));
    }

    private static Integer calculateTotalPrice(List<OrderItemDTO> orderItems) {
        return orderItems.stream()
                .mapToInt(orderItem -> orderItem.price() * orderItem.quantity())
                .sum();
    }
}