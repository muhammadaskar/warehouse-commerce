package com.ecommerce.app.payment.application.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderProcessedRequest {
    private final String orderId;
    private final String orderStatus;
}
