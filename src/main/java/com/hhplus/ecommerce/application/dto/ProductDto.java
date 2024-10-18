package com.hhplus.ecommerce.application.dto;

public record ProductDto(
        Long productId,
        String name,
        Integer price,
        Integer productQuantity
) {
}
