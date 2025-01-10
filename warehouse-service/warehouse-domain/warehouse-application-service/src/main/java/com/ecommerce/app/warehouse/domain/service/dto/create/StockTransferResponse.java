package com.ecommerce.app.warehouse.domain.service.dto.create;

import com.ecommerce.app.warehouse.domain.core.valueobject.StockTransferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class StockTransferResponse {
    private final UUID stockTransferId;
    private final int quantity;
    private final String reason;
    private final WarehouseIdAndNameResponse sourceWarehouse;
    private final WarehouseIdAndNameResponse destinationWarehouse;
    private final ProductResponse product;
    private final StockTransferStatus status;
    private final ZonedDateTime createdAt;
}
