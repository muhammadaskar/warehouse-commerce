package com.ecommerce.app.order.application.service.ports.output.message.publisher.payment;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.order.domain.core.event.OrderPaidEvent;

public interface OrderPaidEventRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
