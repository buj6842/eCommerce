package com.hhplus.ecommerce.application.dto;

import java.util.List;

public record OrderRequest(
        Long userId,
        List<OrderItemDTO> orderItems
) {

}