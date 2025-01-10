package com.ecommerce.app.warehouse.domain.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class WarehouseResponse {
    private final UUID warehouseId;
    private final String name;
    private final WarehouseAddress address;
    private final List<StockResponse> stocks;
}
