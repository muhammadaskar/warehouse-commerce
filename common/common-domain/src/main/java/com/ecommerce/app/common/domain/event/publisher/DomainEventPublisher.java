package com.ecommerce.app.common.domain.event.publisher;

import com.ecommerce.app.common.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T event);
}
