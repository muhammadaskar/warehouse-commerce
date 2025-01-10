package com.ecommerce.app.payment.application.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderConfirmedRequest {
    private final String orderId;
    private final String warehouseId;
    private final String orderStatus;
}
