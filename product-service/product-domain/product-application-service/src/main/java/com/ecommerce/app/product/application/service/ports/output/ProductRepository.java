package com.ecommerce.app.product.application.service.ports.output;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.product.application.service.dto.create.ListProductResponse;
import com.ecommerce.app.product.domain.core.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findByName(String name);
    Optional<Product> findById(ProductId id);
}
