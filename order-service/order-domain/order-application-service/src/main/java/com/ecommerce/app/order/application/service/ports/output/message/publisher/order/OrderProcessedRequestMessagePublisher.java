package com.ecommerce.app.order.application.service.ports.output.message.publisher.order;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.order.domain.core.event.OrderProcessedEvent;

public interface OrderProcessedRequestMessagePublisher extends DomainEventPublisher<OrderProcessedEvent> {
}
