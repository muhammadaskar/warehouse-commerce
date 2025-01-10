package com.ecommerce.app.warehouse.domain.service.ports.output.repository;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.warehouse.domain.core.entity.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(ProductId productId);
}
