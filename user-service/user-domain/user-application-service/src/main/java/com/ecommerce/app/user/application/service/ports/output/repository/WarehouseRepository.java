package com.ecommerce.app.user.application.service.ports.output.repository;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.user.domain.core.entity.Warehouse;

import java.util.Optional;

public interface WarehouseRepository {
    Warehouse save(Warehouse warehouse);

    Optional<Warehouse> findById(WarehouseId warehouseId);
}
