package com.ecommerce.app.order.application.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
    private final UUID orderId;
    private final String orderStatus;
    private final String message;
}
