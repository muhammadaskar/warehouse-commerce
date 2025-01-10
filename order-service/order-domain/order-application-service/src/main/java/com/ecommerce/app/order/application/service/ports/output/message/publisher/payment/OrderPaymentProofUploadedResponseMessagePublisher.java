package com.ecommerce.app.order.application.service.ports.output.message.publisher.payment;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.order.domain.core.event.OrderPaymentProofUploadedEvent;

public interface OrderPaymentProofUploadedResponseMessagePublisher extends DomainEventPublisher<OrderPaymentProofUploadedEvent> {
}
