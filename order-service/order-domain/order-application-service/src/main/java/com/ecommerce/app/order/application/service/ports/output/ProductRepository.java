package com.ecommerce.app.order.application.service.ports.output;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.order.domain.core.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(ProductId productId);
    List<Product> findAllByProductIdWithStock(ProductId productId);
}