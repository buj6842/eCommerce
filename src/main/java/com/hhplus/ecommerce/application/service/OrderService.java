package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.TopOrderProduct;
import com.hhplus.ecommerce.domain.order.Order;
import com.hhplus.ecommerce.domain.order.OrderItem;
import com.hhplus.ecommerce.infrastructure.OrderItemRepository;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    // 주문처리
    public  List<OrderItem> orderProduct(OrderRequest orderRequest) {
        // 주문처리 builder 패턴으로
        Order order = Order.builder()
                .userId(orderRequest.userId())
                .orderDate(LocalDateTime.now())
                .totalPrice(orderRequest.totalPrice())
                .build();

        Order saveOrder = orderRepository.save(order);
        List<OrderItem> orderItems = orderRequest.orderItems().stream()
                .map(orderItemDTO -> OrderItem.builder()
                        .orderId(saveOrder.getOrderId())
                        .productId(orderItemDTO.productId())
                        .quantity(orderItemDTO.quantity())
                        .orderDate(saveOrder.getOrderDate())
                        .build())
                .collect(Collectors.toList());

        return orderItemRepository.saveAll(orderItems);
    }

    // Data Platform 으로 데이터 전송
    public void sendData(OrderRequest orderRequest) {
        // TODO : Data Platform 으로 데이터 전송
    }

    // 3일간 주문량이 많았던 상품 5개 조회
    public List<TopOrderProduct> getTopOrderProduct() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(3);
        return orderItemRepository.findTopOrderProduct(startDate,endDate);
    }

}
