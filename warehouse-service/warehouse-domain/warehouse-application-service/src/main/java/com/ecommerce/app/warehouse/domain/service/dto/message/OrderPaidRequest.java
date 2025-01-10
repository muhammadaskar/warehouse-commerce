package com.ecommerce.app.warehouse.domain.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderPaidRequest {
    private final String orderId;
    private final String warehouseId;
    private final String orderStatus;
    private final List<OrderItem> orderItems;
}
