package com.hhplus.ecommerce.controller;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.TopOrderProduct;
import com.hhplus.ecommerce.application.facade.Facade;
import com.hhplus.ecommerce.config.exception.EcommerceException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final Facade facade;


    @PostMapping("/order")
    @Operation(summary = "상품 주문")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            facade.orderProduct(orderRequest);
            return ResponseEntity.ok("주문이 정상적으로 완료되었습니다.");
        } catch (EcommerceException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @GetMapping("/top-order")
    @Operation(summary = "상위 주문 조회")
    public List<TopOrderProduct> getTopOrder() {
        return facade.getTopOrderProduct();
    }
}

