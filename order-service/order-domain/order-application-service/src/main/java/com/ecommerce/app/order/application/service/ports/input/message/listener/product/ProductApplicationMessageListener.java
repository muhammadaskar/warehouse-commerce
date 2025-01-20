package com.ecommerce.app.order.application.service.ports.input.message.listener.product;

import com.ecommerce.app.order.application.service.dto.message.ProductCreatedRequest;

public interface ProductApplicationMessageListener {
    void createProduct(ProductCreatedRequest productCreatedRequest);
}
