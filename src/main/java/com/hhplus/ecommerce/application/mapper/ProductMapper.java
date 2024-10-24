package com.hhplus.ecommerce.application.mapper;

import com.hhplus.ecommerce.application.dto.ProductDto;
import com.hhplus.ecommerce.domain.product.Product;

public class ProductMapper {
    public static ProductDto toProductDto(Product product) {
        return new ProductDto(product.getProductId(), product.getProductName(), product.getPrice(), product.getProductQuantity());
    }
}
