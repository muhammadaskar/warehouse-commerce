package com.ecommerce.app.warehouse.domain.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {
    private final String productId;
    private final int quantity;
}
