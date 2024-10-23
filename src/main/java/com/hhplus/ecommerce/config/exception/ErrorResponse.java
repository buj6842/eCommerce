package com.hhplus.ecommerce.config.exception;

public record ErrorResponse(
        String code,
        String message
) {
}
