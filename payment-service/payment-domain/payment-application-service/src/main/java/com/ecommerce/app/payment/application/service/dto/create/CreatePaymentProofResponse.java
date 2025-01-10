package com.ecommerce.app.payment.application.service.dto.create;

import com.ecommerce.app.common.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreatePaymentProofResponse {
    private final UUID orderId;
    private final PaymentStatus paymentStatus;
    private final String message;
}
