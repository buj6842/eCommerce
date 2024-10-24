package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.ProductDto;
import com.hhplus.ecommerce.application.dto.TopOrderProduct;
import com.hhplus.ecommerce.application.mapper.ProductMapper;
import com.hhplus.ecommerce.config.exception.EcommerceException;
import com.hhplus.ecommerce.config.exception.ErrorCode;
import com.hhplus.ecommerce.domain.order.OrderItem;
import com.hhplus.ecommerce.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toProductDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void validateProduct(OrderRequest orderRequest) {
        orderRequest.orderItems().forEach(orderItem -> {
            if (productRepository.findById(orderItem.productId())
                    .map(product -> product.getProductQuantity() < orderItem.quantity())
                    .orElse(true)) {
                throw new EcommerceException(ErrorCode.NOT_ENOUGH_PRODUCT_QUANTITY.getCode(), ErrorCode.NOT_ENOUGH_PRODUCT_QUANTITY.getMessage());
            }
        });
    }

    @Transactional
    public void modifyProductQuantity(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> {
            productRepository.findById(orderItem.getProductId())
                    .ifPresent(product -> {
                        product.minusQuantity(orderItem.getQuantity());
                        productRepository.save(product);
                    });
        });
    }



}