package com.ecommerce.app.order.application.service.dto.message;

import com.ecommerce.app.common.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class PaymentApprovedRequest {
    private final String paymentId;
    private final String orderId;
    private final Instant createdAt;
    private final PaymentStatus paymentOrderStatus;
}
