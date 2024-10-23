package com.hhplus.ecommerce.unit;

import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.ProductDto;
import com.hhplus.ecommerce.application.service.ProductService;
import com.hhplus.ecommerce.domain.product.Product;
import com.hhplus.ecommerce.infrastructure.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // Mockito 초기화를 위한 어노테이션 추가
public class ProductUnitTest {

    @Mock
    private ProductRepository productRepository;  // Mock 객체 설정

    @InjectMocks
    private ProductService productService;  // Mock 객체가 주입된 서비스 객체


    private Product product;

    @BeforeEach
    void setUp() {
        // Mock 데이터를 미리 설정
        product = new Product(1L, "핸드폰", 100, 10);
    }

    @Test
    public void 전체_목록_가져오기() {
        // Given: Mock 데이터를 설정
        Product firstProduct = new Product(1L, "케이스", 10000, 50);
        Product secondProduct = new Product(2L, "강화필름", 50000, 30);
        List<Product> productList = new ArrayList<>();
        productList.add(firstProduct);
        productList.add(secondProduct);
        Mockito.when(productRepository.findAll()).thenReturn(productList);  // Mock 동작 설정

        // When: 전체 목록 호출
        List<ProductDto> products = productService.getAllProducts();

        // Then: 데이터 검증
        assertEquals(2, products.size());
        assertEquals("케이스", products.get(0).name());
        assertEquals(10000, products.get(0).price());
        assertEquals(50, products.get(0).productQuantity());
    }

    @Test
    void 제품_재고_정상_실행() {
        // 충분한 재고가 있는 제품 설정
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1, 100)));

        // 제품 검증
        productService.validateProduct(orderRequest);

        // 검증이 제대로 되었는지 확인
        assertTrue(product.getProductQuantity() >= orderRequest.orderItems().stream().mapToInt(OrderItemDTO::quantity).sum());
    }

    @Test
    void 재고_검증_재고가_부족할_때_테스트() {
        // 재고가 부족하도록 설정
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 20, 100)));

        //예외가 발생했는지 확인
        assertThrows(RuntimeException.class, () -> productService.validateProduct(orderRequest));
    }
}
