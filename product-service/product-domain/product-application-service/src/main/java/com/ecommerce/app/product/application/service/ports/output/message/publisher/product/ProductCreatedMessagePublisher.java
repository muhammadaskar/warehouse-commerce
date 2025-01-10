package com.ecommerce.app.product.application.service.ports.output.message.publisher.product;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.product.domain.core.event.ProductCreatedEvent;

public interface ProductCreatedMessagePublisher extends DomainEventPublisher<ProductCreatedEvent> {
}
