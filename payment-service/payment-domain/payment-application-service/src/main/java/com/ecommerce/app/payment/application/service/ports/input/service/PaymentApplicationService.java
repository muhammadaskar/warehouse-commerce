package com.ecommerce.app.payment.application.service.ports.input.service;

import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.payment.application.service.dto.create.*;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import jakarta.validation.Valid;

public interface PaymentApplicationService {
    CreatePaymentProofResponse createPaymentProof(@Valid CreatePaymentProofCommand createPaymentProofCommand, AuthorizationHeader authorizationHeader);
    PaymentResponse getPaymentById(PaymentIdQuery paymentIdQuery, AuthorizationHeader authorizationHeader);
    PaymentResponse getPaymentByOrderId(OrderIdQuery paymentOrderIdQuery, AuthorizationHeader authorizationHeader);
    CreatePaymentApproveResponse approvePayment(@Valid CreatePaymentApproveCommand createPaymentApproveCommand, AuthorizationHeader authorizationHeader);
}
