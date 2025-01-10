package com.ecommerce.app.product.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.product.domain.core.entity.Product;
import com.ecommerce.app.product.domain.core.event.ProductCreatedEvent;

public interface ProductDomainService {
    ProductCreatedEvent createProduct(Product product, DomainEventPublisher<ProductCreatedEvent> productCreatedEventDomainEventPublisher);
}
