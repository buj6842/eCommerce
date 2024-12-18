package com.hhplus.ecommerce.unit;

import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.PointChargeRequest;
import com.hhplus.ecommerce.application.service.UserService;
import com.hhplus.ecommerce.config.exception.EcommerceException;
import com.hhplus.ecommerce.domain.user.User;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    // Mock 데이터를 미리 설정
    private User user = new User(null, "변의진", 10000, LocalDateTime.now());

    @BeforeEach
    void setUp() {
        userRepository.save(user);

    }

    @AfterEach
    void tearDown() {
        // Mock 데이터 초기화
        userRepository.delete(user);
    }

    @Test
    void 포인트_검증_성공() {
        // 포인트가 충분한 사용자 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1)), 100);

        // 검증이 제대로 되었는지 확인
        assertTrue(user.getPoints() > orderRequest.totalPrice());
    }
    @Test
    void 포인트_검증_실패() {
        // 포인트가 부족한 요청 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1), new OrderItemDTO(2L, 2)), 30000);

        //실행과 동시에 예외 발생인지 확인

    }

    @Test
    void 포인트_충전_성공() {
        // 포인트 충전 요청 설정
        when(userRepository.findByIdWithLock(1L)).thenReturn(Optional.of(user));
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1)),100);

        // 포인트 사용

        // 포인트가 제대로 차감되었는지 확인
        assertTrue(user.getPoints() < 10000);
    }

    @Test
    @DisplayName("포인트 충전 실패 : 음수 입력")
    void 포인트_충전_음수_입력시_실패(){
        // 포인트 충전 요청 설정
        PointChargeRequest pointChargeRequest = new PointChargeRequest(1L, -100);

        assertThrows(EcommerceException.class, () -> userService.addPoint(pointChargeRequest));
    }



}