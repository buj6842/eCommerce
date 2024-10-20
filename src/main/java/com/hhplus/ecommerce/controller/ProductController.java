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

    private final Facade facade;

    public ProductController(Facade facade) {
        this.facade = facade;
    }


    @GetMapping("/products")
    @Operation(summary = "상품 목록 조회")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = facade.getAllProducts();
        return ResponseEntity.ok(products);
    }
}

