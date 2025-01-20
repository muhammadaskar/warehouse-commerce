package com.ecommerce.app.order.application.service;

import com.ecommerce.app.order.application.service.dto.message.ProductCreatedRequest;
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
        log.info("ProductHandler.createProduct: {}", productCreatedRequest);
        productHelper.createProduct(productCreatedRequest);
    }
}
