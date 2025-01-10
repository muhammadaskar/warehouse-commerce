package com.ecommerce.app.product.application.service;

import com.ecommerce.app.product.application.service.dto.create.*;
import com.ecommerce.app.product.application.service.ports.input.service.ProductApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Validated
@Service
public class ProductApplicationServiceImpl implements ProductApplicationService {

    private final ProductHandler productHandler;

    public ProductApplicationServiceImpl(ProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    @Override
    public CreateProductResponse createProduct(CreateProductCommand command) {
        return productHandler.createProduct(command);
    }

    @Override
    public List<ListProductResponse> findAllProducts() {
        return productHandler.findAllProducts();
    }

    @Override
    public DetailProductResponse findProductById(ProductIdQuery productIdQuery) {
        return productHandler.findProductById(productIdQuery);
    }
}
