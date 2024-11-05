package com.hhplus.ecommerce.config.exception;

public enum ErrorCode {
    USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다."),
    INVALID_CHARGE_POINT_INPUT(400, "충전할 포인트는 0 이상이어야 합니다."),
    NOT_ENOUGH_POINT(400, "사용자의 포인트가 부족합니다. 충전해주세요"),
    PRODUCT_NOT_FOUND(404, "해당 상품을 찾을 수 없습니다."),
    CART_NOT_FOUND(404, "해당 장바구니를 찾을 수 없습니다."),
    NOT_ENOUGH_PRODUCT_QUANTITY(400, "상품의 재고가 부족합니다."),
    COULD_NOT_ACQUIRE_LOCK(400, "상품 재고를 확인하는 중 문제가 발생했습니다.");
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
