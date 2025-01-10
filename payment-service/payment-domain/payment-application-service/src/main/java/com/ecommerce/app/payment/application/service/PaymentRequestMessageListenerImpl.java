package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.payment.application.service.dto.message.PaymentRequest;
import com.ecommerce.app.payment.application.service.ports.input.message.listener.payment.PaymentRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final OrderHandler orderHandler;
    private final PaymentHandler paymentHandler;

    public PaymentRequestMessageListenerImpl(OrderHandler orderHandler, PaymentHandler paymentHandler) {
        this.orderHandler = orderHandler;
        this.paymentHandler = paymentHandler;
    }

    @Override
    public void createOrderPayment(PaymentRequest paymentRequest) {
        orderHandler.createOrderPayment(paymentRequest);
        log.info("Order Payment created successfully!");
    }
}
