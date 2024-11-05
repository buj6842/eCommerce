package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.PointChargeRequest;
import com.hhplus.ecommerce.config.exception.EcommerceException;
import com.hhplus.ecommerce.config.exception.ErrorCode;
import com.hhplus.ecommerce.domain.order.Order;
import com.hhplus.ecommerce.domain.user.User;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 유저 포인트 충전 전 검증

    public void validateAndUsePoint(OrderRequest orderRequest) {
        User user = userRepository.findByIdWithLock(orderRequest.userId()).orElseThrow(() -> new EcommerceException(ErrorCode.USER_NOT_FOUND.getCode(),ErrorCode.USER_NOT_FOUND.getMessage()));
        if (user.getPoints() < orderRequest.totalPrice()) {
            throw new EcommerceException(ErrorCode.NOT_ENOUGH_POINT.getCode(),ErrorCode.NOT_ENOUGH_POINT.getMessage());
        } else {
            user.usePoints(orderRequest.totalPrice());
            userRepository.save(user);
        }
    }

    // 유저 포인트 추가
    @Transactional
    public void addPoint(PointChargeRequest pointChargeRequest) {
        pointChargeRequest.validate();
        User user = userRepository.findByIdWithLock(pointChargeRequest.userId()).orElseThrow(() -> new EcommerceException(ErrorCode.USER_NOT_FOUND.getCode(),ErrorCode.USER_NOT_FOUND.getMessage()));
        user.addPoints(pointChargeRequest.points());
        userRepository.save(user);
    }

    // 유저 포인트 조회
    @Transactional
    public Integer getPoint(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EcommerceException(ErrorCode.USER_NOT_FOUND.getCode(),ErrorCode.USER_NOT_FOUND.getMessage()));
        return user.getPoints();
    }
}
