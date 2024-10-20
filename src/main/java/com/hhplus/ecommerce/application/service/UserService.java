package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 유저 포인트 조회
    public void validatePoint(OrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.userId()).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        if (user.getPoints() < orderRequest.totalPrice()) {
            throw new RuntimeException("사용자의 포인트가 부족합니다. 충전해주세요");
        }
    }
}
