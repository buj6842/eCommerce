package com.hhplus.ecommerce.inter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.facade.Facade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Facade facade;

    private OrderRequest orderRequest;
    private OrderRequest failedOrderRequest;
    private OrderRequest notEnoughPointsOrderRequest;
    @BeforeEach
    void setUp() {
        // 주문 요청
        orderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 1, 1000)));

        // 재고가 부족한 상품 주문 요청
        failedOrderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(2L, 1, 5000)));

        // 포인트가 부족한 주문 요청
        notEnoughPointsOrderRequest = new OrderRequest(1L, List.of(new OrderItemDTO(1L, 500, 1000)));
    }

    @Test
    void 주문_저장_성공_테스트() throws Exception {
        mockMvc.perform(post("/api/order")
                        .content(objectMapper.writeValueAsString(orderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void 주문_저장_실패_테스트_재고_없음() throws Exception {
        mockMvc.perform(post("/api/order")
                        .content(objectMapper.writeValueAsString(failedOrderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 주문_저장_실패_포인트부족() throws Exception {
        mockMvc.perform(post("/api/order")
                        .content(objectMapper.writeValueAsString(notEnoughPointsOrderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}