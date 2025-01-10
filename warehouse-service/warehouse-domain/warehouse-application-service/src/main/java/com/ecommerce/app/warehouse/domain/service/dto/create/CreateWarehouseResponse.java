package com.ecommerce.app.warehouse.domain.service.dto.create;

import com.ecommerce.app.common.domain.valueobject.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateWarehouseResponse {
    private final UUID warehouseId;
    private final String warehouseName;
    private final Address warehouseAddress;
    private final String message;
}
