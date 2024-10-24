package com.hhplus.ecommerce.application.facade;

import com.hhplus.ecommerce.application.dto.*;
import com.hhplus.ecommerce.application.service.CartService;
import com.hhplus.ecommerce.application.service.OrderService;
import com.hhplus.ecommerce.application.service.ProductService;
import com.hhplus.ecommerce.application.service.UserService;
import com.hhplus.ecommerce.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void orderProduct(OrderRequest orderRequest) {
        // 유저 포인트 조회 및 주문전체 금액과 비교
        userService.validatePoint(orderRequest);
        // 재고 확인
        productService.validateProduct(orderRequest);
        // 주문처리
        List<OrderItem> orderItems = orderService.orderProduct(orderRequest);
        // 유저 포인트 차감
        userService.usePoint(orderRequest);
        // 상품 재고 차감
        productService.modifyProductQuantity(orderItems);
        // Data Platform 으로 데이터 전송
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