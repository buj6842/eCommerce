package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.PointChargeRequest;
import com.hhplus.ecommerce.domain.user.User;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 유저 포인트 충전 전 검증
    @Transactional
    public void validatePoint(OrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.userId()).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        if (user.getPoints() < orderRequest.totalPrice()) {
            throw new RuntimeException("사용자의 포인트가 부족합니다. 충전해주세요");
        }
    }

    // 유저 포인트 차감
    @Transactional
    public void usePoint(OrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.userId()).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        user.usePoints(orderRequest.totalPrice());
        userRepository.save(user);
    }

    // 유저 포인트 추가
    @Transactional
    public void addPoint(PointChargeRequest pointChargeRequest) {
        pointChargeRequest.validate();
        User user = userRepository.findById(pointChargeRequest.userId()).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        user.addPoints(pointChargeRequest.points());
        userRepository.save(user);
    }

    // 유저 포인트 조회
    @Transactional
    public Integer getPoint(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return user.getPoints();
    }
}
