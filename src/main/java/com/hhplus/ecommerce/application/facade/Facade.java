package com.hhplus.ecommerce.application.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.ecommerce.application.dto.*;
import com.hhplus.ecommerce.application.event.OrderEvent;
import com.hhplus.ecommerce.application.service.CartService;
import com.hhplus.ecommerce.application.service.OrderService;
import com.hhplus.ecommerce.application.service.ProductService;
import com.hhplus.ecommerce.application.service.UserService;
import com.hhplus.ecommerce.domain.outbox.OutboxEvent;
import com.hhplus.ecommerce.infrastructure.OutBoxEventRepository;
import com.hhplus.ecommerce.infrastructure.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class Facade {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;
    private final ApplicationEventPublisher eventPublisher;
    private final OutBoxEventRepository outBoxEventRepository;
    private final KafkaProducer kafkaProducer;


    // 상품 조회
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // 상품 주문

    @Transactional
    public void orderProduct(OrderRequest orderRequest) throws JsonProcessingException {

            int totalPrice = productService.getProductTotalPrice(orderRequest);
            productService.validateProduct(orderRequest);
            userService.validateAndUsePoint(orderRequest, totalPrice);
            Long orderId = orderService.orderProduct(orderRequest,totalPrice);
            OutboxEvent outboxEvent = OutboxEvent.builder()
                        .status(OutboxEvent.OutboxStatus.INIT)
                        .payload(toJson(new OrderEvent(orderId,  orderRequest)))
                        .eventType(OutboxEvent.EventType.ORDER)
                        .build();
            outBoxEventRepository.save(outboxEvent);
    }


    // 유저 포인트 조회
    public Integer getPoint(Long userId) {
        return userService.getPoint(userId);
    }

    // 유저 포인트 추가
    public void addPoint(PointChargeRequest pointChargeRequest) {
        userService.addPoint(pointChargeRequest);
    }

    // 장바구니에 상품 추가
    public void addProductToCart(CartRequest cartRequest) {
        cartService.addProductToCart(cartRequest);
    }

    // 장바구니에서 상품 삭제
    public void removeProductFromCart(Long cartId) {
        cartService.removeProductFromCart(cartId);
    }

    // 장바구니 상품 수량 변경
    public void updateProductQuantity(CartUpdateRequest cartUpdateRequest) {
        cartService.updateProductQuantity(cartUpdateRequest);
    }

    public List<TopOrderProduct> getTopOrderProduct() {
        return orderService.getTopOrderProduct();
    }

    public String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}