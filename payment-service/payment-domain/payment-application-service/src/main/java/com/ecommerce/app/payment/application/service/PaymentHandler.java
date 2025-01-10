package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.payment.application.service.dto.create.*;
import com.ecommerce.app.payment.application.service.dto.message.PaymentRequest;
import com.ecommerce.app.payment.application.service.mapper.PaymentDataMapper;
import com.ecommerce.app.payment.application.service.ports.output.message.publisher.payment.PaymentApprovedMessagePublisher;
import com.ecommerce.app.payment.application.service.ports.output.message.publisher.payment.PaymentProofUploadedMessagePublisher;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import com.ecommerce.app.payment.domain.core.event.PaymentApprovedEvent;
import com.ecommerce.app.payment.domain.core.event.PaymentProofUploadedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentHandler {

    private final PaymentHelper paymentHelper;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentProofUploadedMessagePublisher paymentProofUploadedMessagePublisher;
    private final PaymentApprovedMessagePublisher paymentApprovedMessagePublisher;

    public PaymentHandler(PaymentHelper paymentHelper, PaymentDataMapper paymentDataMapper, PaymentProofUploadedMessagePublisher paymentProofUploadedMessagePublisher, PaymentApprovedMessagePublisher paymentApprovedMessagePublisher) {
        this.paymentHelper = paymentHelper;
        this.paymentDataMapper = paymentDataMapper;
        this.paymentProofUploadedMessagePublisher = paymentProofUploadedMessagePublisher;
        this.paymentApprovedMessagePublisher = paymentApprovedMessagePublisher;
    }

    public void createPayment(PaymentRequest paymentRequest) {
        log.info("Creating payment for order with id: {}", paymentRequest.getOrderId());
        paymentHelper.createPayment(paymentRequest);
        log.info("Payment created for order with id: {}", paymentRequest.getOrderId());
    }

    public CreatePaymentProofResponse createPaymentProof(CreatePaymentProofCommand createPaymentProofCommand, AuthorizationHeader authorizationHeader) {
        log.info("Creating payment proof for order with id: {}", createPaymentProofCommand.getOrderId());
        PaymentProofUploadedEvent paymentProofUploadedEvent = paymentHelper.createPaymentProof(createPaymentProofCommand, authorizationHeader);
        paymentProofUploadedMessagePublisher.publish(paymentProofUploadedEvent);
        return paymentDataMapper.paymentToCreatePaymentProofResponse(paymentProofUploadedEvent.getPayment(), "Payment proof uploaded successfully");
    }

    public PaymentResponse getPaymentById(PaymentIdQuery paymentIdQuery, AuthorizationHeader authorizationHeader) {
        return paymentDataMapper.paymentToPaymentResponse(paymentHelper.findPaymentById(paymentIdQuery, authorizationHeader));
    }

    public PaymentResponse getPaymentByOrderId(OrderIdQuery paymentOrderIdQuery, AuthorizationHeader authorizationHeader) {
        return paymentDataMapper.paymentToPaymentResponse(paymentHelper.findPaymentByOrderId(paymentOrderIdQuery, authorizationHeader));
    }

    public CreatePaymentApproveResponse approvePayment(CreatePaymentApproveCommand createPaymentApproveCommand, AuthorizationHeader authorizationHeader) {
        log.info("Approving payment for order with id: {}", createPaymentApproveCommand.getPaymentId());
        PaymentApprovedEvent paymentApprovedEvent = paymentHelper.approvePayment(createPaymentApproveCommand, authorizationHeader);
        paymentApprovedMessagePublisher.publish(paymentApprovedEvent);
        return paymentDataMapper.paymentToApprovePaymentResponse(paymentApprovedEvent.getPayment(), "Payment approved successfully");
    }
}
