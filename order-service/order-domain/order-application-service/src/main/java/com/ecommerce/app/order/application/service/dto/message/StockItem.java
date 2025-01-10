package com.ecommerce.app.order.application.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StockItem {
    private final String stockId;
    private final int quantity;
}
