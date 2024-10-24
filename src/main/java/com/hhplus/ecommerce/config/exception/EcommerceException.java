package com.hhplus.ecommerce.config.exception;

import lombok.Getter;

@Getter
public class EcommerceException extends RuntimeException{

    private final int errorCode;
    private final String message;


    public EcommerceException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

}
