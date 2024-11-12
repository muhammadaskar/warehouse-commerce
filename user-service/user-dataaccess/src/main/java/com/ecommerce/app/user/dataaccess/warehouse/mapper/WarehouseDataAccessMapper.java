package com.ecommerce.app.user.dataaccess.warehouse.mapper;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.user.dataaccess.warehouse.entity.WarehouseEntity;
import com.ecommerce.app.user.domain.core.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseDataAccessMapper {
    public WarehouseEntity warehouseToWarehouseEntity(Warehouse warehouse) {
        return WarehouseEntity.builder()
                .id(warehouse.getId().getValue())
                .build();
    }

    public Warehouse warehouseEntityToWarehouse(WarehouseEntity warehouseEntity) {
        return Warehouse.builder()
                .withId(new WarehouseId(warehouseEntity.getId()))
                .build();
    }
}
