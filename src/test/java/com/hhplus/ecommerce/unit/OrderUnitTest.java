package com.hhplus.ecommerce.unit;

import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.service.OrderService;
import com.hhplus.ecommerce.domain.order.Order;
import com.hhplus.ecommerce.infrastructure.OrderItemRepository;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        // Mock 데이터를 미리 설정
        order = new Order(1L, 1L, LocalDateTime.now(), 100);
    }

    @Test
    void 주문_저장_성공() {
        // Given: 유효한 주문 설정
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1, 100)));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // When: 주문 저장
        orderService.orderProduct(orderRequest);

        // Then: 저장이 성공했는지 확인
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).saveAll(anyList());
    }
}