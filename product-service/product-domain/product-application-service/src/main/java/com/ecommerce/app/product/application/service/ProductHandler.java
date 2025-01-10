package com.ecommerce.app.product.application.service;

import com.ecommerce.app.product.application.service.dto.create.*;
import com.ecommerce.app.product.application.service.mapper.ProductDataMapper;
import com.ecommerce.app.product.application.service.ports.output.message.publisher.product.ProductCreatedMessagePublisher;
import com.ecommerce.app.product.domain.core.entity.Product;
import com.ecommerce.app.product.domain.core.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductHandler {

    private final ProductHelper productHelper;
    private final ProductDataMapper productDataMapper;
    private final ProductCreatedMessagePublisher productCreatedMessagePublisher;

    public ProductHandler(ProductHelper productHelper, ProductDataMapper productDataMapper, ProductCreatedMessagePublisher productCreatedMessagePublisher) {
        this.productHelper = productHelper;
        this.productDataMapper = productDataMapper;
        this.productCreatedMessagePublisher = productCreatedMessagePublisher;
    }

    public CreateProductResponse createProduct(CreateProductCommand createProductCommand) {
        ProductCreatedEvent productCreatedEvent = productHelper.persistProduct(createProductCommand);
        productCreatedMessagePublisher.publish(productCreatedEvent);
        return productDataMapper.productToCreateProductResponse(productCreatedEvent.getProduct(), "Product created successfully!");
    }

    public List<ListProductResponse> findAllProducts() {
        return productDataMapper.productsToListProductResponse(productHelper.findAllProducts());
    }

    public DetailProductResponse findProductById(ProductIdQuery productId) {
        return productDataMapper.productToDetailProductResponse(productHelper.findProductById(productId));
    }
}
