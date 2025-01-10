package com.ecommerce.app.payment.application.service.dto.create;

import com.ecommerce.app.common.domain.valueobject.OrderStatus;
import com.ecommerce.app.common.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponse {
    private final UUID paymentId;
    private final UUID orderId;
    private final Float amount;
    private final PaymentStatus paymentStatus;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final OrderStatus orderStatus;
    private final String paymentProof;
}
