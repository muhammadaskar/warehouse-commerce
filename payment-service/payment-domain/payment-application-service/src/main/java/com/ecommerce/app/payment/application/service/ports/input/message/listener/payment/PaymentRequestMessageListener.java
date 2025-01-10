package com.ecommerce.app.payment.application.service.ports.input.message.listener.payment;

import com.ecommerce.app.payment.application.service.dto.message.PaymentRequest;

public interface PaymentRequestMessageListener {
    void createOrderPayment(PaymentRequest paymentRequest);
}
