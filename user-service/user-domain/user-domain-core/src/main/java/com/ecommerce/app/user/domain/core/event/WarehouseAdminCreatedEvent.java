package com.ecommerce.app.user.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.user.domain.core.entity.User;

import java.time.ZonedDateTime;

public class WarehouseAdminCreatedEvent implements DomainEvent {
    private final User user;
    private final ZonedDateTime createdAt;
    private final DomainEventPublisher<WarehouseAdminCreatedEvent> warehouseAdminCreatedEventDomainEventPublisher;

    public WarehouseAdminCreatedEvent(User user, ZonedDateTime createdAt, DomainEventPublisher<WarehouseAdminCreatedEvent> warehouseAdminCreatedEventDomainEventPublisher) {
        this.user = user;
        this.createdAt = createdAt;
        this.warehouseAdminCreatedEventDomainEventPublisher = warehouseAdminCreatedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        warehouseAdminCreatedEventDomainEventPublisher.publish(this);
    }

    public User getUser() {
        return user;
    }
}
