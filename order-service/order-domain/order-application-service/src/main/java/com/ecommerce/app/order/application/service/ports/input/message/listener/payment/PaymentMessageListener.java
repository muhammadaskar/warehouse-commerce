package com.ecommerce.app.order.application.service.ports.input.message.listener.payment;

import com.ecommerce.app.order.application.service.dto.message.PaymentApprovedRequest;
import com.ecommerce.app.order.application.service.dto.message.PaymentProofUploadRequest;

public interface PaymentMessageListener {
    void paymentProofUploaded(PaymentProofUploadRequest paymentProofUploadRequest);
    void approveOrder(PaymentApprovedRequest paymentApprovedRequest);
}
