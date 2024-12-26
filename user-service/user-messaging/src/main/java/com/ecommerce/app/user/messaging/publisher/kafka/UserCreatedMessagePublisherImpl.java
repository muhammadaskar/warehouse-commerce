package com.ecommerce.app.user.messaging.publisher.kafka;

import com.ecommerce.app.user.application.service.ports.output.message.publisher.user.UserCreatedMessagePublisher;
import com.ecommerce.app.user.domain.core.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserCreatedMessagePublisherImpl implements UserCreatedMessagePublisher {
    @Override
    public void publish(UserCreatedEvent event) {

    }
}
