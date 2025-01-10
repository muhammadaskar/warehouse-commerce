package com.ecommerce.app.order.application.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
public class StockUpdated {
    private final String stockId;
    private final int quantity;
    private ZonedDateTime createdAt;
}
