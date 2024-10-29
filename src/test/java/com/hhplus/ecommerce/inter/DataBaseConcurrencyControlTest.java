package com.hhplus.ecommerce.inter;

import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.facade.Facade;
import com.hhplus.ecommerce.domain.order.Order;
import com.hhplus.ecommerce.domain.product.Product;
import com.hhplus.ecommerce.domain.user.User;
import com.hhplus.ecommerce.infrastructure.OrderRepository;
import com.hhplus.ecommerce.infrastructure.ProductRepository;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class DataBaseConcurrencyControlTest {
    @Autowired
    private Facade facade;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;


    @BeforeEach
    void setUp() {
        // Mock 데이터를 미리 설정
        user = new User(null, "테스터", 500000000, LocalDateTime.now());
        userRepository.save(user);

        // Product 데이터 설정
        Product product1 = new Product(null, "갤럭시s24", 5000, 500);
        Product product2 = new Product(null, "아이폰17", 100000, 200);
        Product product3 = new Product(null, "아이패드", 50000, 100);
        Product product4 = new Product(null, "갤럭시탭", 800000, 3000);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
    }


    @Test
    @Transactional
    void 동시성_테스트_DB_Lock() throws InterruptedException {
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1)), 100);

        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    facade.orderProduct(orderRequest);
                } catch (Exception e) {
                    // 예외 발생 시 무시
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();

        System.out.println("실행 시간: " + (endTime - startTime) + "ms");

        // order 테이블 검증
        List<Order> orders = orderRepository.findByUserId(user.getUserId());
        assertEquals(10, orders.size(), "오더 개수 10");

        // product 테이블 검증
        Product product = productRepository.findByProductName("갤럭시s24");
        assertEquals(490, product.getProductQuantity(), "상품 재고 작성");

        // user 테이블 검증
        User updatedUser = userRepository.findById(user.getUserId()).orElseThrow();
        assertEquals(499999000, updatedUser.getPoints(), "유저의 포인트 499999000");
    }
}
