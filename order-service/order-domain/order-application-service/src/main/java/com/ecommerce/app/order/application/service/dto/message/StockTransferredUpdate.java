package com.ecommerce.app.order.application.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class StockTransferredUpdate {
    private final String stockIdWarehouseFrom;
    private final Integer quantityUpdatedFromWarehouse;
    private final String stockIdWarehouseTo;
    private final Integer quantityUpdatedToWarehouse;
    private final Instant updatedAt;
}
