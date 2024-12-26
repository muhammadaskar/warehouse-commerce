package com.ecommerce.app.user.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.user.domain.core.entity.User;

import java.time.ZonedDateTime;

public class UserCreatedEvent implements DomainEvent {
    private final User user;
    private final ZonedDateTime createdAt;
    private final DomainEventPublisher<UserCreatedEvent> userCreatedEventDomainEventPublisher;

    public UserCreatedEvent(User user, ZonedDateTime createdAt, DomainEventPublisher<UserCreatedEvent> userCreatedEventDomainEventPublisher) {
        this.user = user;
        this.createdAt = createdAt;
        this.userCreatedEventDomainEventPublisher = userCreatedEventDomainEventPublisher;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void fire() {
        userCreatedEventDomainEventPublisher.publish(this);
    }
}
