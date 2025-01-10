package com.ecommerce.app.product.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.product.domain.core.entity.Product;
import com.ecommerce.app.product.domain.core.event.ProductCreatedEvent;

import java.time.ZonedDateTime;

public class ProductDomainServiceImpl implements ProductDomainService {

    @Override
    public ProductCreatedEvent createProduct(Product product, DomainEventPublisher<ProductCreatedEvent> productCreatedEventDomainEventPublisher) {
        product.initializeProduct();
        return new ProductCreatedEvent(product, ZonedDateTime.now(), productCreatedEventDomainEventPublisher);
    }
}
