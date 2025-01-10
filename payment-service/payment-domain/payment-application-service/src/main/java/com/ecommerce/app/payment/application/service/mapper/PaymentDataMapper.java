package com.ecommerce.app.payment.application.service.mapper;

import com.ecommerce.app.common.domain.valueobject.Money;
import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.common.domain.valueobject.PaymentId;
import com.ecommerce.app.payment.application.service.dto.create.*;
import com.ecommerce.app.payment.application.service.dto.message.PaymentRequest;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import com.ecommerce.app.common.domain.valueobject.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {

    public Payment paymentRequestToPayment(PaymentRequest paymentRequest) {
        return Payment.newBuilder()
                .withId(new PaymentId(UUID.fromString(paymentRequest.getId())))
                .withOrderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .withAmount(new Money(paymentRequest.getPrice()))
                .withStatus(PaymentStatus.AWAITING_PAYMENT)
                .build();
    }

    public Payment createPaymentProofToPayment(CreatePaymentProofCommand createPaymentProofCommand) {
        return Payment.newBuilder()
                .withOrderId(new OrderId(createPaymentProofCommand.getOrderId()))
                .withPaymentProof(createPaymentProofCommand.getPaymentProof())
                .build();
    }

    public PaymentRequest paymentToPaymentRequest(Payment payment) {
        return PaymentRequest.builder()
                .id(payment.getId().getValue().toString())
                .orderId(payment.getOrderId().getValue().toString())
                .price(payment.getAmount().getAmount())
                .paymentStatus(payment.getStatus())
                .build();
    }

    public CreatePaymentProofResponse paymentToCreatePaymentProofResponse(Payment payment, String message) {
        return CreatePaymentProofResponse.builder()
                .orderId(payment.getOrderId().getValue())
                .paymentStatus(payment.getStatus())
                .message(message)
                .build();
    }

    public Payment approvePaymentToPayment(CreatePaymentApproveCommand paymentRequest) {
        return Payment.newBuilder()
                .withId(new PaymentId(paymentRequest.getPaymentId()))
                .build();
    }

    public CreatePaymentApproveCommand paymentToApprovePayment(Payment payment) {
        return CreatePaymentApproveCommand.builder()
                .paymentId(payment.getId().getValue())
                .build();
    }

    public CreatePaymentApproveResponse paymentToApprovePaymentResponse(Payment payment, String message) {
        return CreatePaymentApproveResponse.builder()
                .paymentId(payment.getId().getValue())
                .message(message)
                .build();
    }

    public PaymentResponse paymentToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId().getValue())
                .orderId(payment.getOrderId().getValue())
                .amount(payment.getAmount().getAmount().floatValue())
                .paymentProof(payment.getPaymentProof())
                .paymentStatus(payment.getStatus())
                .orderStatus(payment.getOrder().getStatus())
                .createdAt(payment.getCretedAt().toInstant())
                .updatedAt(payment.getUpdatedAt().toInstant())
                .build();
    }
}
