package com.ecommerce.app.order.application.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StockShippedUpdate {
    private final List<StockItem> stockItems;
}
