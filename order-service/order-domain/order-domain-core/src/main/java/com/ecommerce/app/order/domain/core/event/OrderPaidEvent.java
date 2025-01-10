package com.ecommerce.app.order.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.order.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent implements DomainEvent {

    private final Order order;
    private final ZonedDateTime updatedAt;
    private final DomainEventPublisher<OrderPaidEvent> domainEventPublisher;

    public OrderPaidEvent(Order order, ZonedDateTime updatedAt, DomainEventPublisher<OrderPaidEvent> domainEventPublisher) {
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
