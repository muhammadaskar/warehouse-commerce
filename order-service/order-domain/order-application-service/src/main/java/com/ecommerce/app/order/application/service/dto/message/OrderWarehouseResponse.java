package com.ecommerce.app.order.application.service.dto.message;

import com.ecommerce.app.common.domain.valueobject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class OrderWarehouseResponse {
    private final String orderId;
    private final String warehouseId;
    private final Instant createdAt;
    private final OrderStatus orderStatus;
}
