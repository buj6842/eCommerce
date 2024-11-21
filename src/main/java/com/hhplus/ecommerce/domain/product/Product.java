package com.hhplus.ecommerce.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Integer price;
    private Integer productQuantity;

    public Product(Long productId, String productName, Integer price, Integer productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productQuantity = productQuantity;
    }

    public void minusQuantity(Integer quantity) {
        this.productQuantity -= quantity;
    }

    public void plusQuantity(Integer quantity) {
        this.productQuantity += quantity;
    }
}

