package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        // Mock 데이터를 미리 설정
      user = new User(1L, "변의진", 10000, LocalDateTime.now());

    }

    @Test
    void 포인트_검증_성공() {
        // 포인트가 충분한 사용자 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1, 100)));

        // 포인트 검증
        userService.validatePoint(orderRequest);

        // 검증이 제대로 되었는지 확인
        assertTrue(user.getPoints() > orderRequest.totalPrice());
    }
    @Test
    void 포인트_검증_실패() {
        // 포인트가 부족한 요청 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1, 1000000), new OrderItemDTO(2L, 2, 20000000)));

        //실행과 동시에 예외 발생인지 확인
        assertThrows(RuntimeException.class, () -> userService.validatePoint(orderRequest));
    }
}