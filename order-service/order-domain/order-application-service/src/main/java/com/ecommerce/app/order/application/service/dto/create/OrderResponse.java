package com.ecommerce.app.order.application.service.dto.create;

import com.ecommerce.app.common.domain.valueobject.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrderResponse {
    private final UUID orderId;
    private final UUID userId;
    private final UUID cartId;
    private final Float totalPrice;
    private final List<OrderItemResponse> items;
    private final OrderAddress shippingAddress;
    private final String orderStatus;
    private final Instant createdAt;
    private final Instant updatedAt;
}
