package com.ecommerce.app.payment.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.payment.domain.core.entity.Order;
import com.ecommerce.app.payment.domain.core.entity.Payment;

import java.time.ZonedDateTime;

public class PaymentProofUploadedEvent extends PaymentEvent {
    private final DomainEventPublisher<PaymentProofUploadedEvent> paymentProofUploadedEventDomainEventPublisher;

    public PaymentProofUploadedEvent(Payment payment, ZonedDateTime createdAt, DomainEventPublisher<PaymentProofUploadedEvent> paymentProofUploadedEventDomainEventPublisher) {
        super(payment, createdAt);
        this.paymentProofUploadedEventDomainEventPublisher = paymentProofUploadedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        paymentProofUploadedEventDomainEventPublisher.publish(this);
    }
}
