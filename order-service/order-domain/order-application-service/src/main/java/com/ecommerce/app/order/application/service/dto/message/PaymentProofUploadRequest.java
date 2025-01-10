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
public class PaymentProofUploadRequest {
    private final String userId;
    private final String orderId;
    private final BigDecimal price;
    private final Instant createdAt;
    private final PaymentStatus paymentStatus;
}
