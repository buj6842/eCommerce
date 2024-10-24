package com.hhplus.ecommerce.application.dto;

public record CartUpdateRequest(
        Long cartId,
        Long productId,
        Integer quantity
) {
}
