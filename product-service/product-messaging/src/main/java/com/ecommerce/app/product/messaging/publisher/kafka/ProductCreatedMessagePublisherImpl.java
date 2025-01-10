package com.ecommerce.app.product.messaging.publisher.kafka;

import com.ecommerce.app.product.application.service.ports.output.message.publisher.product.ProductCreatedMessagePublisher;
import com.ecommerce.app.product.domain.core.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductCreatedMessagePublisherImpl implements ProductCreatedMessagePublisher {
    @Override
    public void publish(ProductCreatedEvent event) {
        log.info("ProductCreatedMessagePublisherImpl: publish: event: {}", event);
    }
}
