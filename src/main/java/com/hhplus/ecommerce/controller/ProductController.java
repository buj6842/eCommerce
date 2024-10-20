package com.hhplus.ecommerce.controller;

import com.hhplus.ecommerce.application.dto.ProductDto;
import com.hhplus.ecommerce.application.facade.Facade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        // 상품 목록 조회 로직 추가
        // Dummy Data
        List<ProductDto> products = List.of(
                new ProductDto(1L, "RTX 4070Ti", 5000.0, 10),
                new ProductDto(2L, "RTX 4090", 8000.0, 5)
        );
        return ResponseEntity.ok(products);
    }
}

