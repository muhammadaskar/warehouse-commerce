package com.ecommerce.app.payment.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import com.ecommerce.app.payment.domain.core.entity.User;
import com.ecommerce.app.payment.domain.core.event.PaymentApprovedEvent;
import com.ecommerce.app.payment.domain.core.event.PaymentProofUploadedEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class PaymentDomainServiceImpl implements PaymentDomainService{

    @Override
    public void createPayment(Payment payment) {
        payment.initializePaymentRequest();
    }

    @Override
    public PaymentProofUploadedEvent createPaymentProof(User user, Payment payment, String paymentProof, DomainEventPublisher<PaymentProofUploadedEvent> paymentProofUploadedEventDomainEventPublisher) {
        payment.updatePaymentProof(user, paymentProof);
        return new PaymentProofUploadedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), paymentProofUploadedEventDomainEventPublisher);
    }

    @Override
    public PaymentApprovedEvent approvePayment(User user, Payment payment, DomainEventPublisher<PaymentApprovedEvent> paymentApprovedEventDomainEventPublisher) {
        payment.approvePayment(user);
        return new PaymentApprovedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), paymentApprovedEventDomainEventPublisher);
    }
}
