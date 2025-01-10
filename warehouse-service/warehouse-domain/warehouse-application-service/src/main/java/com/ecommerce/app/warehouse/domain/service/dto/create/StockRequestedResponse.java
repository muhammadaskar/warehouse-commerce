package com.ecommerce.app.warehouse.domain.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class StockRequestedResponse {
    private final UUID warehouseId;
    private final UUID productId;
    private final String message;
}
