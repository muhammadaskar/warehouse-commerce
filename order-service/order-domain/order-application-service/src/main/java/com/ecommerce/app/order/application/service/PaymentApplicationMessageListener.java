package com.ecommerce.app.order.application.service;

import com.ecommerce.app.order.application.service.dto.message.PaymentApprovedRequest;
import com.ecommerce.app.order.application.service.dto.message.PaymentProofUploadRequest;
import com.ecommerce.app.order.application.service.ports.input.message.listener.payment.PaymentMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class PaymentApplicationMessageListener implements PaymentMessageListener {

    private final OrderHandler orderHandler;

    public PaymentApplicationMessageListener(OrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @Override
    public void paymentProofUploaded(PaymentProofUploadRequest paymentProofUploadRequest) {
        orderHandler.paymentProofUploaded(paymentProofUploadRequest);
    }

    @Override
    public void approveOrder(PaymentApprovedRequest paymentApprovedRequest) {
        orderHandler.updateOrderToApproved(paymentApprovedRequest);
    }
}
