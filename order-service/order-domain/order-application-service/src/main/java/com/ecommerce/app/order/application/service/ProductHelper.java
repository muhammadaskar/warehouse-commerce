package com.ecommerce.app.order.application.service;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.order.application.service.dto.message.ProductCreatedRequest;
import com.ecommerce.app.order.application.service.mapper.OrderDataMapper;
import com.ecommerce.app.order.application.service.ports.output.ProductRepository;
import com.ecommerce.app.order.domain.core.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ProductHelper {

    private final ProductRepository productRepository;
    private final OrderDataMapper orderDataMapper;

    public ProductHelper(ProductRepository productRepository, OrderDataMapper orderDataMapper) {
        this.productRepository = productRepository;
        this.orderDataMapper = orderDataMapper;
    }

    public Product createProduct(ProductCreatedRequest productCreatedRequest) {
        Product product = orderDataMapper.productCreatedRequestToProduct(productCreatedRequest);
        return productRepository.save(product);
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
