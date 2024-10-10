package com.hhplus.ecommerce.controller;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        double totalCost = orderRequest.orderItems().stream()
                .mapToDouble(item -> item.price() * item.quantity())
                .sum();

        if (totalCost <= 1000.0) { // Mock 잔액 체크
            return ResponseEntity.ok("주문 성공: 총 가격 " + totalCost + "원");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("잔액이 부족합니다.");
        }
    }
}

