package com.ecommerce.app.warehouse.dataaccess.warehouse.mapper;

import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.dataaccess.warehouse.entity.WarehouseEntity;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseDataAccessMapper {

    public WarehouseEntity warehouseToWarehouseEntity(Warehouse warehouse) {
        return WarehouseEntity.builder()
                .id(warehouse.getId().getValue())
                .name(warehouse.getName())
                .street(warehouse.getAddress().getStreet())
                .postalCode(warehouse.getAddress().getPostalCode())
                .city(warehouse.getAddress().getCity())
                .build();
    }

    public Warehouse warehouseEntityToWarehouseEntity(WarehouseEntity warehouseEntity) {
        return Warehouse.builder()
                .withId(new WarehouseId(warehouseEntity.getId()))
                .withName(warehouseEntity.getName())
                .withAddress(warehouseAddressToStreetAddress(warehouseEntity))
                .build();
    }

    private Address warehouseAddressToStreetAddress(WarehouseEntity warehouseEntity) {
        return new Address(
                warehouseEntity.getStreet(),
                warehouseEntity.getPostalCode(),
                warehouseEntity.getCity()
        );
    }
}
