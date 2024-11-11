package com.ecommerce.app.user.dataaccess.warehouse.adapter;

import com.ecommerce.app.user.dataaccess.warehouse.repository.WarehouseJpaRepository;

public class WarehouseRepositoryImpl {
    private final WarehouseJpaRepository warehouseJpaRepository;

    public WarehouseRepositoryImpl(WarehouseJpaRepository warehouseJpaRepository) {
        this.warehouseJpaRepository = warehouseJpaRepository;
    }
}
