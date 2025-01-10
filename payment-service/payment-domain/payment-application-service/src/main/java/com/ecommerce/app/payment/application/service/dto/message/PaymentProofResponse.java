package com.ecommerce.app.payment.application.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class PaymentProofResponse {
    private final String orderId;
    private final Instant updatedAt;
    private final String orderStatus;
}
