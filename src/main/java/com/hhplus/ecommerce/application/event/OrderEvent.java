package com.hhplus.ecommerce.application.event;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderEvent {
    private final Long orderId;
    private final String status; // "SUCCESS" or "FAILED"
    private final OrderRequest orderRequest;
}
