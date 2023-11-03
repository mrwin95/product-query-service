package com.winca.controller;

import com.winca.dto.ProductEvent;
import com.winca.entity.Product;
import com.winca.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductQueryService productQueryService;

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> productList = productQueryService.getProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
