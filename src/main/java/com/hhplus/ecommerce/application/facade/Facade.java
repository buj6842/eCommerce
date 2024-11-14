package com.hhplus.ecommerce.application.facade;

import com.hhplus.ecommerce.application.dto.*;
import com.hhplus.ecommerce.application.service.CartService;
import com.hhplus.ecommerce.application.service.OrderService;
import com.hhplus.ecommerce.application.service.ProductService;
import com.hhplus.ecommerce.application.service.UserService;
import com.hhplus.ecommerce.domain.order.Order;
import com.hhplus.ecommerce.domain.order.OrderItem;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class Facade {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;


    // 상품 조회
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // 상품 주문

    @Transactional
    public void orderProduct(OrderRequest orderRequest) {
        productService.validateProduct(orderRequest);
        userService.validateAndUsePoint(orderRequest);
        List<OrderItem> orderItems = orderService.orderProduct(orderRequest);
        orderService.sendData(orderRequest);
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

}