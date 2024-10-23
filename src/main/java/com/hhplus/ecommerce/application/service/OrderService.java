package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.domain.order.Order;
import com.hhplus.ecommerce.domain.order.OrderItem;
import com.hhplus.ecommerce.infrastructure.OrderItemRepository;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    // 주문처리
    @Transactional
    public void orderProduct(OrderRequest orderRequest) {
        Integer totalPrice = orderRequest.orderItems().stream()
                .mapToInt(orderItem -> orderItem.price() * orderItem.quantity())
                .sum();
        // 주문처리 builder 패턴으로
        Order order = Order.builder()
                .userId(orderRequest.userId())
                .orderDate(LocalDateTime.now())
                .totalPrice(totalPrice)
                .build();

        Order saveOrder = orderRepository.save(order);
        List<OrderItem> orderItems = orderRequest.orderItems().stream()
                .map(orderItemDTO -> OrderItem.builder()
                        .orderId(saveOrder.getOrderId())
                        .productId(orderItemDTO.productId())
                        .quantity(orderItemDTO.quantity())
                        .price(orderItemDTO.price())
                        .build())
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
    }

}
