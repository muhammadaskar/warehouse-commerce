package com.ecommerce.app.warehouse.domain.service.ports.input.message.listener.product;

import com.ecommerce.app.warehouse.domain.service.dto.message.ProductCreatedRequest;

public interface ProductApplicationMessageListener {
    void createProduct(ProductCreatedRequest productCreatedRequest);
}
