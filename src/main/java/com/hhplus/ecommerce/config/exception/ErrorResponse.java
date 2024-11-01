package com.hhplus.ecommerce.config.exception;

public record ErrorResponse(
        int code,
        String message
) {
}
