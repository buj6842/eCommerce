package com.hhplus.ecommerce.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.TopOrderProduct;
import com.hhplus.ecommerce.domain.order.Order;
import com.hhplus.ecommerce.domain.order.OrderItem;
import com.hhplus.ecommerce.infrastructure.OrderItemRepository;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String TOP_ORDER_PRODUCT_CACHE_KEY = "topOrderProduct";

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

    public List<TopOrderProduct> getTopOrderProduct() {

        Object data = redisTemplate.opsForValue().get(TOP_ORDER_PRODUCT_CACHE_KEY);

        if (data != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(data, new TypeReference<List<TopOrderProduct>>() {
            });
        }

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(3);
        List<TopOrderProduct> topOrderProducts = orderItemRepository.findTopOrderProduct(startDate, endDate);

        redisTemplate.opsForValue().set(TOP_ORDER_PRODUCT_CACHE_KEY, topOrderProducts, 600, TimeUnit.SECONDS);
        return topOrderProducts;
    }
}
