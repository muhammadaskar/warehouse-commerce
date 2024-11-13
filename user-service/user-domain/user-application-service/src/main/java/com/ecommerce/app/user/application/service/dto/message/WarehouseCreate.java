package com.ecommerce.app.user.application.service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WarehouseCreate {
    private final String warehouseId;
}
