package com.ecommerce.app.payment.domain.core.event;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.payment.domain.core.entity.Payment;

import java.time.ZonedDateTime;

public class PaymentApprovedEvent extends PaymentEvent{

    private final DomainEventPublisher<PaymentApprovedEvent> domainEventPublisher;

    public PaymentApprovedEvent(Payment payment, ZonedDateTime createdAt, DomainEventPublisher<PaymentApprovedEvent> domainEventPublisher) {
        super(payment, createdAt);
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public void fire() {
        domainEventPublisher.publish(this);
    }
}
