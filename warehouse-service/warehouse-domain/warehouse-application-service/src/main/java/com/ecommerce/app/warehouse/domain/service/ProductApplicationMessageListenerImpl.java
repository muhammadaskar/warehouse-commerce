package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.service.dto.message.ProductCreatedRequest;
import com.ecommerce.app.warehouse.domain.service.ports.input.message.listener.product.ProductApplicationMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class ProductApplicationMessageListenerImpl implements ProductApplicationMessageListener {

    private final ProductHandler productHandler;

    public ProductApplicationMessageListenerImpl(ProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    @Override
    public void createProduct(ProductCreatedRequest productCreatedRequest) {
        productHandler.createProduct(productCreatedRequest);
    }
}
