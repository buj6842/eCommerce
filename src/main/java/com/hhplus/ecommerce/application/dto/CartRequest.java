package com.hhplus.ecommerce.application.dto;

public record CartRequest(
        Long userId,
        Long productId,
        Integer quantity
) {
}
