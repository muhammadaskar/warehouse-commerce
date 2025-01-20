package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.service.dto.message.ProductCreatedRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductHandler {

    private final ProductHelper productHelper;

    public ProductHandler(ProductHelper productHelper) {
        this.productHelper = productHelper;
    }

    public void createProduct(ProductCreatedRequest productCreatedRequest) {
        productHelper.createProduct(productCreatedRequest);
    }
}
