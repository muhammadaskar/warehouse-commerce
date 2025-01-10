package com.ecommerce.app.payment.application.service.dto.message;

import com.ecommerce.app.common.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PaymentRequest {
    private final String id;
    private final String userId;
    private final String warehouseId;
    private final String orderId;
    private final BigDecimal price;
    private final Instant createdAt;
    private final PaymentStatus paymentStatus;
}
