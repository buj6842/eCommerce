package com.hhplus.ecommerce.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.TopOrderProduct;
import com.hhplus.ecommerce.application.service.OrderService;
import com.hhplus.ecommerce.domain.order.Order;
import com.hhplus.ecommerce.infrastructure.OrderItemRepository;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

//    @BeforeEach
//    void setUp() {
//        // Mock 데이터를 미리 설정
//        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
//        order = new Order(1L, 1L, LocalDateTime.now(), 100);
//    }

    @Test
    void 주문_저장_성공() {
        // Given: 유효한 주문 설정
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1)), 100);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // When: 주문 저장
        orderService.orderProduct(orderRequest);

        // Then: 저장이 성공했는지 확인
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).saveAll(anyList());
    }

    @Test
    void 상위_상품_조회_성공() {
        // Given: Redis에 캐싱된 데이터가 없는 경우
        when(valueOperations.get("topOrderProducts")).thenReturn(null);

        // Mock 데이터 설정
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(3);
        List<TopOrderProduct> mockTopOrderProducts = List.of(
                new TopOrderProduct(1L, "Product1", 10L),
                new TopOrderProduct(2L, "Product2", 5L)
        );
        lenient().when(orderItemRepository.findTopOrderProduct(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(mockTopOrderProducts);

        // When: 상위 상품 조회
        List<TopOrderProduct> topOrderProducts = orderService.getTopOrderProduct();

        // Then: 상위 상품 조회 성공
        assertEquals(2, topOrderProducts.size());
        verify(orderItemRepository, times(1)).findTopOrderProduct(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(valueOperations, times(1)).set("topOrderProducts", mockTopOrderProducts, 10, TimeUnit.MINUTES);
    }

    @Test
    void 상위_상품_조회_캐시_성공() {
        // Given: Redis에 캐시된 데이터가 있는 경우
        List<TopOrderProduct> cachedTopOrderProducts = List.of(
                new TopOrderProduct(1L, "Product1", 10L),
                new TopOrderProduct(2L, "Product2", 5L)
        );
        when(valueOperations.get("topOrderProducts")).thenReturn(cachedTopOrderProducts);
        // When: 상위 상품 조회
        List<TopOrderProduct> topOrderProducts = orderService.getTopOrderProduct();

        // Then: 캐시된 데이터가 반환되는지 확인
        assertEquals(2, topOrderProducts.size());
        verify(orderItemRepository, times(0)).findTopOrderProduct(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void testSerialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // 직렬화 테스트용 데이터 작성
        List<TopOrderProduct> products = List.of(new TopOrderProduct(1L, "Product Name", 5L));

        // JSON 변환 테스트
        String jsonResult = mapper.writeValueAsString(products);
        System.out.println(jsonResult);
    }
}