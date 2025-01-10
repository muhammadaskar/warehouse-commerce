package com.ecommerce.app.product.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.product.domain.core.entity.Product;

import java.time.ZonedDateTime;

public class ProductCreatedEvent implements DomainEvent {
    private final Product product;
    private final ZonedDateTime createdAt;
    private final DomainEventPublisher<ProductCreatedEvent> productCreatedEventDomainEventPublisher;

    public ProductCreatedEvent(Product product, ZonedDateTime createdAt, DomainEventPublisher<ProductCreatedEvent> productCreatedEventDomainEventPublisher) {
        this.product = product;
        this.createdAt = createdAt;
        this.productCreatedEventDomainEventPublisher = productCreatedEventDomainEventPublisher;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public void fire() {
        productCreatedEventDomainEventPublisher.publish(this);
    }
}
