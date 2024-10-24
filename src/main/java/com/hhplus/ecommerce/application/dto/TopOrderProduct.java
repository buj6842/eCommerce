package com.hhplus.ecommerce.application.dto;

public record TopOrderProduct(
        Long productId,
        String name,
        Long salesQuantity
) {
}
