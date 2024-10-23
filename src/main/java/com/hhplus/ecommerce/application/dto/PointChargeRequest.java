package com.hhplus.ecommerce.application.dto;

public record PointChargeRequest(
        Long userId,
        Integer points
) {

    public void validate() {
        if (points < 0) {
            throw new RuntimeException("충전할 포인트는 0 이상이어야 합니다.");
        }
    }
}
