package com.ecommerce.app.warehouse.domain.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class StockTransferCommand {
    private final UUID warehouseId;
    private final UUID productId;
    private final int quantity;
}
