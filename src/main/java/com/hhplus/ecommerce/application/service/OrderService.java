package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderItem;
import com.hhplus.ecommerce.infrastructure.OrderItemRepository;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
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

        order = orderRepository.save(order);

        // 주문상품 처리
        // OrderItems 생성 및 저장
        Order saveOrder = order;
        List<OrderItem> orderItems = orderRequest.orderItems().stream()
                .map(orderItemDTO -> OrderItem.builder()
                        .order(saveOrder)
                        .productId(orderItemDTO.productId())
                        .quantity(orderItemDTO.quantity())
                        .price(orderItemDTO.price())
                        .build())
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
    }

}
