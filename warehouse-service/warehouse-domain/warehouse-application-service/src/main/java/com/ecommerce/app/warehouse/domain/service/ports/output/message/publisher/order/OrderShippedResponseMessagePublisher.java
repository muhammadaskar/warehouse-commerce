package com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.order;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.warehouse.domain.core.event.OrderShippedResponseEvent;

public interface OrderShippedResponseMessagePublisher extends DomainEventPublisher<OrderShippedResponseEvent> {
}
