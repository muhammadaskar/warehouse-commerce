package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Order;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderShippedResponseEvent implements DomainEvent {
    private final Order order;
    private final ZonedDateTime updatedAt;
    private final DomainEventPublisher<OrderShippedResponseEvent> domainEventPublisher;

    public OrderShippedResponseEvent(Order order, ZonedDateTime updatedAt, DomainEventPublisher<OrderShippedResponseEvent> domainEventPublisher) {
        this.order = order;
        this.updatedAt = updatedAt;
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
