package com.hhplus.ecommerce.application.service;

import com.hhplus.ecommerce.application.dto.OrderItemDTO;
import com.hhplus.ecommerce.application.dto.OrderRequest;
import com.hhplus.ecommerce.application.dto.ProductDto;
import com.hhplus.ecommerce.application.mapper.ProductMapper;
import com.hhplus.ecommerce.config.exception.EcommerceException;
import com.hhplus.ecommerce.config.exception.ErrorCode;
import com.hhplus.ecommerce.domain.product.Product;
import com.hhplus.ecommerce.infrastructure.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toProductDto)
                .collect(Collectors.toList());
    }

    // 검증후 상품 재고 차감 후 총합포인트 반환

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

    @Transactional
    public void restoreProduct(OrderRequest orderRequest) {
        for (OrderItemDTO item : orderRequest.orderItems()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new EcommerceException(ErrorCode.PRODUCT_NOT_FOUND.getCode(), ErrorCode.PRODUCT_NOT_FOUND.getMessage()));
            product.plusQuantity(item.quantity());
            productRepository.save(product);
        }
    }


    public Integer getProductTotalPrice(OrderRequest orderRequest) {
        List<OrderItemDTO> orderItemDTOS = orderRequest.orderItems();
        AtomicInteger totalPrice = new AtomicInteger();
        List<Long> list = orderItemDTOS.stream().map(orderItemDTO -> orderItemDTO.productId()).toList();
        List<Product> productsByIds = productRepository.findProductsInProductIds(list);
        productsByIds.forEach(product -> {
            OrderItemDTO orderItemDTO = orderItemDTOS.stream().filter(orderItemDTO1 -> orderItemDTO1.productId().equals(product.getProductId())).findFirst().get();
            totalPrice.addAndGet(product.getPrice() * orderItemDTO.quantity());
        });
        return totalPrice.get();
    }

}