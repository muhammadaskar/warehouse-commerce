package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.payment.application.service.dto.create.*;
import com.ecommerce.app.payment.application.service.ports.input.service.PaymentApplicationService;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class PaymentApplicationServiceImpl implements PaymentApplicationService {

    private final PaymentHandler paymentHandler;

    public PaymentApplicationServiceImpl(PaymentHandler paymentHandler) {
        this.paymentHandler = paymentHandler;
    }

    @Override
    public CreatePaymentProofResponse createPaymentProof(CreatePaymentProofCommand createPaymentProofCommand, AuthorizationHeader authorizationHeader) {
        return paymentHandler.createPaymentProof(createPaymentProofCommand, authorizationHeader);
    }

    @Override
    public PaymentResponse getPaymentById(PaymentIdQuery paymentIdQuery, AuthorizationHeader authorizationHeader) {
        return paymentHandler.getPaymentById(paymentIdQuery, authorizationHeader);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(OrderIdQuery paymentOrderIdQuery, AuthorizationHeader authorizationHeader) {
        return paymentHandler.getPaymentByOrderId(paymentOrderIdQuery, authorizationHeader);
    }

    @Override
    public CreatePaymentApproveResponse approvePayment(CreatePaymentApproveCommand createPaymentApproveCommand, AuthorizationHeader authorizationHeader) {
        return paymentHandler.approvePayment(createPaymentApproveCommand, authorizationHeader);
    }
}
