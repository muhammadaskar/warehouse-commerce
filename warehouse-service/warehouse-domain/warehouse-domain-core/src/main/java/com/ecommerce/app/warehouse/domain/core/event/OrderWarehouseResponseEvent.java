package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderWarehouseResponseEvent implements DomainEvent {

    private final Order order;
    private final ZonedDateTime createdAt;
    private final DomainEventPublisher<OrderWarehouseResponseEvent> domainEventPublisher;

    public OrderWarehouseResponseEvent(Order order, ZonedDateTime createdAt, DomainEventPublisher<OrderWarehouseResponseEvent> domainEventPublisher) {
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
