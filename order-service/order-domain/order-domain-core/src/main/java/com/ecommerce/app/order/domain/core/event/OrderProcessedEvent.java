package com.ecommerce.app.order.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.order.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderProcessedEvent implements DomainEvent {
    private final Order order;
    private final ZonedDateTime createdAt;
    private final DomainEventPublisher<OrderProcessedEvent> domainEventPublisher;

    public OrderProcessedEvent(Order order, ZonedDateTime createdAt, DomainEventPublisher<OrderProcessedEvent> domainEventPublisher) {
        this.order = order;
        this.createdAt = createdAt;
        this.domainEventPublisher = domainEventPublisher;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public void fire() {
        domainEventPublisher.publish(this);
    }
}
