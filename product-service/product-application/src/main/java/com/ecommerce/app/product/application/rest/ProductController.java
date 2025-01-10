package com.ecommerce.app.product.application.rest;

import com.ecommerce.app.common.application.security.annotation.RequiresRole;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.product.application.service.dto.create.*;
import com.ecommerce.app.product.application.service.ports.input.service.ProductApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/products", produces = "application/vnd.api.v1+json")
public class ProductController {

    private final ProductApplicationService productApplicationService;

    public ProductController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN, UserRole.CUSTOMER})
    @GetMapping
    public ResponseEntity<List<ListProductResponse>> getProducts() {
        List<ListProductResponse> products = productApplicationService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN, UserRole.CUSTOMER})
    @GetMapping(value = "/{productId}")
    public ResponseEntity<DetailProductResponse> getProduct(@PathVariable("productId") UUID productId) {
        DetailProductResponse product = productApplicationService.findProductById(
                ProductIdQuery.builder().productId(productId).build());
        return ResponseEntity.ok(product);
    }

    @RequiresRole(UserRole.SUPER_ADMIN)
    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductCommand createProductCommand) {
        CreateProductResponse createProductResponse = productApplicationService.createProduct(createProductCommand);
        return ResponseEntity.created(null).body(createProductResponse);
    }
}
