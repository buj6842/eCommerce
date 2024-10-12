package com.hhplus.ecommerce.controller;

import com.hhplus.ecommerce.application.dto.CartRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(@RequestBody CartRequest cartRequest) {
        // 장바구니에 상품 추가 로직
        return ResponseEntity.ok(("장바구니에 상품이 추가되었습니다."));
    }

    @PatchMapping("/remove")
    public ResponseEntity<String> removeItemFromCart(@RequestBody CartRequest cartRequestDto) {
        // 장바구니에서 상품 제거 로직
        return ResponseEntity.ok(("장바구니에서 상품이 제거되었습니다."));
    }
}
