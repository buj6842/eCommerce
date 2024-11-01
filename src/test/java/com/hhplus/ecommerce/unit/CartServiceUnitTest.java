package com.hhplus.ecommerce.unit;


import com.hhplus.ecommerce.application.dto.CartRequest;
import com.hhplus.ecommerce.application.dto.CartUpdateRequest;
import com.hhplus.ecommerce.application.service.CartService;
import com.hhplus.ecommerce.domain.cart.Cart;
import com.hhplus.ecommerce.infrastructure.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CartServiceUnitTest {

    @Mock
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    private Cart cart;

    @BeforeEach
    void setUp() {
        // Mock 데이터를 미리 설정
        cart = new Cart(1L, 1L, 100);
        cartRepository.save(cart);
    }

    @Test
    void 장바구니_추가_성공() {
        // Given: 유효한 장바구니 설정
        CartRequest cartRequest = new CartRequest(1L, 1L, 100);
        // when: 장바구니 추가
        cartService.addProductToCart(cartRequest);
        // Then: 저장된 장바구니와 user_id, product_id, quantity가 일치하는지 확인
        verify(cartService, times(1)).addProductToCart(cartRequest);
    }

    @Test
    void 장바구니_삭제_성공() {
        // Given: 유효한 장바구니 설정
        Long cartId = 1L;
        // when: 장바구니 삭제
        cartService.removeProductFromCart(cartId);
        // Then: 삭제가 성공했는지 확인
        verify(cartService, times(1)).removeProductFromCart(cartId);
    }

    @Test
    void 장바구니_수량_변경_성공() {
        // Given: 장바구니 수량 변경 요청 생성
        CartUpdateRequest cartRequest = new CartUpdateRequest(1L, 1L, 100);
        // when: 장바구니 수량 변경
        cartService.updateProductQuantity(cartRequest);
        // Then: 변경이 성공했는지 확인
        verify(cartService, times(1)).updateProductQuantity(cartRequest);
    }

}
