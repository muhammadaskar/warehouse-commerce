package com.ecommerce.app.user.dataaccess.warehouse.adapter;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.user.dataaccess.warehouse.mapper.WarehouseDataAccessMapper;
import com.ecommerce.app.user.dataaccess.warehouse.repository.WarehouseUserJpaRepository;
import com.ecommerce.app.user.domain.core.entity.Warehouse;
import com.ecommerce.app.user.application.service.ports.output.repository.WarehouseRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WarehouseRepositoryImpl implements WarehouseRepository {
    private final WarehouseUserJpaRepository warehouseJpaRepository;
    private final WarehouseDataAccessMapper warehouseDataAccessMapper;

    public WarehouseRepositoryImpl(WarehouseUserJpaRepository warehouseJpaRepository, WarehouseDataAccessMapper warehouseDataAccessMapper) {
        this.warehouseJpaRepository = warehouseJpaRepository;
        this.warehouseDataAccessMapper = warehouseDataAccessMapper;
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        return warehouseDataAccessMapper.warehouseEntityToWarehouse(
                warehouseJpaRepository.save(warehouseDataAccessMapper.warehouseToWarehouseEntity(warehouse)));
    }

    @Override
    public Optional<Warehouse> findByWarehouseId(WarehouseId warehouseId) {
        return warehouseJpaRepository.findById(warehouseId.getValue()).map(
                warehouseDataAccessMapper::warehouseEntityToWarehouse
        );
    }
}
