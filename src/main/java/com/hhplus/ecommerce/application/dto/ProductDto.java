package com.hhplus.ecommerce.application.dto;

public record ProductDto(
        Long productId,
        String name,
        Double price,
        Integer productQuantity
) {
}
