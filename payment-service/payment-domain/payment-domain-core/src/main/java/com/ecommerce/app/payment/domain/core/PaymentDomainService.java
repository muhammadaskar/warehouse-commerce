package com.ecommerce.app.payment.domain.core;

import com.ecommerce.app.common.domain.event.publisher.DomainEventPublisher;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import com.ecommerce.app.payment.domain.core.entity.User;
import com.ecommerce.app.payment.domain.core.event.PaymentApprovedEvent;
import com.ecommerce.app.payment.domain.core.event.PaymentProofUploadedEvent;

public interface PaymentDomainService {
    void createPayment(Payment payment);
    PaymentProofUploadedEvent createPaymentProof(User user, Payment payment, String paymentProof, DomainEventPublisher<PaymentProofUploadedEvent> paymentProofUploadedEventDomainEventPublisher);
    PaymentApprovedEvent approvePayment(User user, Payment payment, DomainEventPublisher<PaymentApprovedEvent> paymentApprovedEventDomainEventPublisher);
}
