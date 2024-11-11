package com.ecommerce.app.warehouse.domain.core.event;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;

import java.time.ZonedDateTime;

public abstract class WarehouseEvent implements DomainEvent {
    private final Warehouse warehouse;
    private final ZonedDateTime createdAt;

    public WarehouseEvent(Warehouse warehouse, ZonedDateTime createdAt) {
        this.warehouse = warehouse;
        this.createdAt = createdAt;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
