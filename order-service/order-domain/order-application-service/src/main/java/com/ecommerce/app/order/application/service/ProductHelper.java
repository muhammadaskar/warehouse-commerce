package com.ecommerce.app.order.application.service;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.order.application.service.ports.output.ProductRepository;
import com.ecommerce.app.order.domain.core.entity.Product;
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
     * Find product by id
     * @param productId product id
     * @return product
     */
    public Product findProduct(ProductId productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            log.error("Product with id {} not found", productId);
            throw new RuntimeException("Product not found");
        }
        return product.get();
    }
}
