package com.ecommerce.app.payment.application.service.ports.output.message.publisher.payment;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.payment.domain.core.event.PaymentApprovedEvent;

public interface PaymentApprovedMessagePublisher extends DomainEventPublisher<PaymentApprovedEvent> {
}
