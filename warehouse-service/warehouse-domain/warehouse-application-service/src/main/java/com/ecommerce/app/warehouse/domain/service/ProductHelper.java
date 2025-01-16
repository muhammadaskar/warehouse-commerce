package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.warehouse.domain.core.entity.Product;
import com.ecommerce.app.warehouse.domain.core.exception.ProductNotFoundException;
import com.ecommerce.app.warehouse.domain.service.dto.message.ProductCreatedRequest;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ProductHelper {

    private final ProductRepository productRepository;
    private final WarehouseDataMapper warehouseDataMapper;

    public ProductHelper(ProductRepository productRepository, WarehouseDataMapper warehouseDataMapper) {
        this.productRepository = productRepository;
        this.warehouseDataMapper = warehouseDataMapper;
    }

    /**
     * Create product
     * @param productCreatedRequest ProductCreatedRequest
     * @return Product
     */
    public Product createProduct(ProductCreatedRequest productCreatedRequest) {
        Product product = warehouseDataMapper.productCreatedRequestToProduct(productCreatedRequest);
        return productRepository.save(product);
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
