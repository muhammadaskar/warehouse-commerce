package com.ecommerce.app.user.application.service.ports.output.message.publisher.user;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.user.domain.core.event.UserCreatedEvent;

public interface UserCreatedMessagePublisher extends DomainEventPublisher<UserCreatedEvent> {
}
