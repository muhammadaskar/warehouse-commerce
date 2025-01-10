package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.warehouse.domain.core.entity.Product;
import com.ecommerce.app.warehouse.domain.core.exception.ProductNotFoundException;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ProductHelper {

    private final ProductRepository productRepository;

    public ProductHelper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Find product by productId
     * @param productId ProductId
     * @return Product
     */
    public Product findProduct(ProductId productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            log.error("Product with id {} not found", productId.getValue());
            throw new ProductNotFoundException("Product not found");
        }
        return product.get();
    }
}
