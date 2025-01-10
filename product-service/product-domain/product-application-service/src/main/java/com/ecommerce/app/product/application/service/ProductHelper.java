package com.ecommerce.app.product.application.service;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.product.application.service.dto.create.CreateProductCommand;
import com.ecommerce.app.product.application.service.dto.create.ListProductResponse;
import com.ecommerce.app.product.application.service.dto.create.ProductIdQuery;
import com.ecommerce.app.product.application.service.mapper.ProductDataMapper;
import com.ecommerce.app.product.application.service.ports.output.ProductRepository;
import com.ecommerce.app.product.application.service.ports.output.message.publisher.product.ProductCreatedMessagePublisher;
import com.ecommerce.app.product.domain.core.ProductDomainService;
import com.ecommerce.app.product.domain.core.entity.Product;
import com.ecommerce.app.product.domain.core.event.ProductCreatedEvent;
import com.ecommerce.app.product.domain.core.exception.ProductException;
import com.ecommerce.app.product.domain.core.exception.ProductNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class ProductHelper {

    private final ProductDomainService productDomainService;
    private final ProductRepository productRepository;
    private final ProductCreatedMessagePublisher productCreatedMessagePublisher;
    private final ProductDataMapper productDataMapper;

    public ProductHelper(ProductDomainService productDomainService, ProductRepository productRepository, ProductCreatedMessagePublisher productCreatedMessagePublisher, ProductDataMapper productDataMapper) {
        this.productDomainService = productDomainService;
        this.productRepository = productRepository;
        this.productCreatedMessagePublisher = productCreatedMessagePublisher;
        this.productDataMapper = productDataMapper;
    }

    /**
     * Persist product
     *
     * @param createProductCommand CreateProductCommand
     * @return ProductCreatedEvent
     */
    @Transactional
    public ProductCreatedEvent persistProduct(CreateProductCommand createProductCommand) {
        log.info("Creating product with name: {}", createProductCommand.getName());
        Product product = productDataMapper.productCommandToProduct(createProductCommand);
        ProductCreatedEvent productCreatedEvent = productDomainService.createProduct(product, productCreatedMessagePublisher);
        log.info("product.image: {}", product.getImageUrl());
        saveProduct(product);
        return productCreatedEvent;
    }

    /**
     * Find all products
     *
     * @return ListProductResponse
     */
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        log.info("Finding all products");
        return productRepository.findAll();
    }

    /**
     * Find product by id
     *
     * @param productIdQuery ProductIdQuery
     * @return Product
     */
    @Transactional(readOnly = true)
    public Product findProductById(ProductIdQuery productIdQuery) {
        log.info("Finding product by id: {}", productIdQuery.getProductId());
        return productRepository.findById(new ProductId(productIdQuery.getProductId())).orElseThrow(() -> new ProductNotFound("Product with id " + productIdQuery.getProductId() + " not found!"));
    }

    /**
     * Find product by name
     *
     * @param product Product
     * @return Product
     */
    private Product saveProduct(Product product) {
        Product searchProduct = productRepository.findByName(product.getName()).orElse(null);
        if (searchProduct != null) {
            throw new ProductException("Product with name " + product.getName() + " already exists!");
        }

        Product productResult = productRepository.save(product);
        log.info("productResult.image: {}", productResult.getImageUrl());
        if (productResult == null) {
            throw new ProductException("Product could not be saved!");
        }
        return productResult;
    }
}
