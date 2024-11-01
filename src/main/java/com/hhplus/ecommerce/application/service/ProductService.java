package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.ProductDto;
import com.hhplus.ecommerce.application.dto.TopOrderProduct;
import com.hhplus.ecommerce.application.mapper.ProductMapper;
import com.hhplus.ecommerce.config.exception.EcommerceException;
import com.hhplus.ecommerce.config.exception.ErrorCode;
import com.hhplus.ecommerce.domain.order.OrderItem;
import com.hhplus.ecommerce.domain.product.Product;
import com.hhplus.ecommerce.infrastructure.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toProductDto)
                .collect(Collectors.toList());
    }

    // 검증후 상품 재고 차감
    @Transactional
    public void validateProduct(OrderRequest orderRequest) {
        for (OrderItemDTO item : orderRequest.orderItems()) {
            Product product = productRepository.findByIdWithLock(item.productId())
                    .orElseThrow(() -> new EcommerceException(ErrorCode.PRODUCT_NOT_FOUND.getCode(), ErrorCode.PRODUCT_NOT_FOUND.getMessage()));
            if (product.getProductQuantity() < item.quantity()) {
                throw new EcommerceException(ErrorCode.NOT_ENOUGH_PRODUCT_QUANTITY.getCode(), ErrorCode.NOT_ENOUGH_PRODUCT_QUANTITY.getMessage());
            } else {
                product.minusQuantity(item.quantity());
                productRepository.save(product);
            }
        }
    }


}