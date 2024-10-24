package com.hhplus.ecommerce.application.dto;

import com.hhplus.ecommerce.config.exception.EcommerceException;
import com.hhplus.ecommerce.config.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public record PointChargeRequest(
        Long userId,
        Integer points
) {

    public void validate() {
        if (points < 0) {
            throw new EcommerceException(ErrorCode.INVALID_CHARGE_POINT_INPUT.getCode(), ErrorCode.INVALID_CHARGE_POINT_INPUT.getMessage());
        }
    }
}
