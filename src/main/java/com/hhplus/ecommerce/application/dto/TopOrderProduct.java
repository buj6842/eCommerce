package com.hhplus.ecommerce.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TopOrderProduct(
        @JsonProperty("productId") Long productId,
        @JsonProperty("name") String name,
        @JsonProperty("salesQuantity") Long salesQuantity
) {
    @JsonCreator
    public TopOrderProduct {

    }
}