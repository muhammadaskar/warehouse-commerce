package com.ecommerce.app.order.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.order.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderConfirmedEvent implements DomainEvent {
    private final Order order;
    private final ZonedDateTime updateAt;
    private final DomainEventPublisher<OrderConfirmedEvent> domainEventPublisher;

    public OrderConfirmedEvent(Order order, ZonedDateTime updateAt, DomainEventPublisher<OrderConfirmedEvent> domainEventPublisher) {
        this.order = order;
        this.updateAt = updateAt;
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
