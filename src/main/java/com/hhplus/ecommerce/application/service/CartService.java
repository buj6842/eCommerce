package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.CartRequest;
import com.hhplus.ecommerce.application.dto.CartUpdateRequest;
import com.hhplus.ecommerce.config.exception.EcommerceException;
import com.hhplus.ecommerce.config.exception.ErrorCode;
import com.hhplus.ecommerce.domain.cart.Cart;
import com.hhplus.ecommerce.infrastructure.CartRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    @Transactional
    public void addProductToCart(CartRequest cartRequest) {
        // 장바구니에 상품 추가
        Cart cart = Cart.builder()
                .userId(cartRequest.userId())
                .productId(cartRequest.productId())
                .quantity(cartRequest.quantity())
                .build();

        cartRepository.save(cart);
    }

    @Transactional
    public void removeProductFromCart(Long cartId) {
        // 장바구니에서 상품 삭제
        cartRepository.deleteById(cartId);
    }

    @Transactional
    public void updateProductQuantity(CartUpdateRequest cartUpdateRequest) {
        // 장바구니 상품 수량 변경
        Cart cart = cartRepository.findById(cartUpdateRequest.cartId())
                .orElseThrow(() -> new EcommerceException(ErrorCode.CART_NOT_FOUND.getCode(), ErrorCode.CART_NOT_FOUND.getMessage()));

        cart.updateCart(cartUpdateRequest.productId(), cartUpdateRequest.quantity());

    }
}
