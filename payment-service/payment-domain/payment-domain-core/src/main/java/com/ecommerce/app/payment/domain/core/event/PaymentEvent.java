package com.ecommerce.app.payment.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.payment.domain.core.entity.Payment;

import java.time.ZonedDateTime;

public abstract class PaymentEvent implements DomainEvent {
    private final Payment payment;
    private final ZonedDateTime createdAt;

    protected PaymentEvent(Payment payment, ZonedDateTime createdAt) {
        this.payment = payment;
        this.createdAt = createdAt;
    }

    public Payment getPayment() {
        return payment;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
