package com.ecommerce.app.order.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.order.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent implements DomainEvent {
    private final Order order;
    private final ZonedDateTime createdAt;
    private final DomainEventPublisher<OrderCreatedEvent> domainEventPublisher;

    public OrderCreatedEvent(Order order, ZonedDateTime createdAt, DomainEventPublisher<OrderCreatedEvent> domainEventPublisher) {
        this.order = order;
        this.createdAt = createdAt;
        this.domainEventPublisher = domainEventPublisher;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void fire() {
        domainEventPublisher.publish(this);
    }
}
