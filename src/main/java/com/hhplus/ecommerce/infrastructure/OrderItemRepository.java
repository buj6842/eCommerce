package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.application.dto.TopOrderProduct;
import com.hhplus.ecommerce.domain.order.OrderItem;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 3일간 판매량이 가장 많았던 상품 5개 조회
    @Transactional
    @Query("SELECT new com.hhplus.ecommerce.application.dto.TopOrderProduct(oi.productId, p.productName, SUM(oi.quantity)) " +
            "FROM OrderItem oi JOIN Product p ON oi.productId = p.productId " +
            "WHERE oi.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY oi.productId, p.productName, p.price " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<TopOrderProduct> findTopOrderProduct(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
