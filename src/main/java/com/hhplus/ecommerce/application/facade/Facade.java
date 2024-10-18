package com.hhplus.ecommerce.application.facade;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.ProductDto;
import com.hhplus.ecommerce.application.service.OrderService;
import com.hhplus.ecommerce.application.service.ProductService;
import com.hhplus.ecommerce.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Facade {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

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
        orderService.orderProduct(orderRequest);

    }

}