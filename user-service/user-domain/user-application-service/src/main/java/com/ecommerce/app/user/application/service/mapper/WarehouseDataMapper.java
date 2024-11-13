package com.ecommerce.app.user.application.service.mapper;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.user.application.service.dto.message.WarehouseCreate;
import com.ecommerce.app.user.domain.core.entity.Warehouse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WarehouseDataMapper {
    public Warehouse warehouseCreateToWarehouse(WarehouseCreate warehouseCreate) {
        return Warehouse.builder()
                .withId(new WarehouseId(UUID.fromString(warehouseCreate.getWarehouseId())))
                .build();
    }
}
