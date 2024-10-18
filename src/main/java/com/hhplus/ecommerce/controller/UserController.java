package com.hhplus.ecommerce.controller;


import com.hhplus.ecommerce.application.dto.PointChargeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @GetMapping("/{userId}/point")
    public ResponseEntity<Integer> getPoint(@PathVariable Long userId) {
        // 포인트 조회 로직
        return ResponseEntity.ok(10000);
    }

    @PostMapping("/point/charge")
    public ResponseEntity<String> chargePoint(@RequestBody PointChargeRequest requestDto) {
        // 포인트 충전 로직
        return ResponseEntity.ok("포인트 충전 성공: " + requestDto.points() + "포인트가 추가되었습니다.");
    }
}

