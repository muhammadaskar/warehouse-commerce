package com.ecommerce.app.payment.application.service.ports.output;

import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.common.domain.valueobject.PaymentId;
import com.ecommerce.app.payment.domain.core.entity.Payment;

import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(PaymentId paymentId);
    Optional<Payment> findByOrderId(OrderId orderId);
}
