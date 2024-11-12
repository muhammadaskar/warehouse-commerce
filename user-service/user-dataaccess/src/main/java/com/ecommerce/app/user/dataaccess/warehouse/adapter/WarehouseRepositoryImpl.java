package com.ecommerce.app.user.dataaccess.warehouse.adapter;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.user.application.service.ports.output.repository.WarehouseRepository;
import com.ecommerce.app.user.dataaccess.warehouse.mapper.WarehouseDataAccessMapper;
import com.ecommerce.app.user.dataaccess.warehouse.repository.WarehouseJpaRepository;
import com.ecommerce.app.user.domain.core.entity.Warehouse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WarehouseRepositoryImpl implements WarehouseRepository {

    private final WarehouseJpaRepository warehouseJpaRepository;
    private final WarehouseDataAccessMapper warehouseDataAccessMapper;

    public WarehouseRepositoryImpl(WarehouseJpaRepository warehouseJpaRepository, WarehouseDataAccessMapper warehouseDataAccessMapper) {
        this.warehouseJpaRepository = warehouseJpaRepository;
        this.warehouseDataAccessMapper = warehouseDataAccessMapper;
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        return warehouseDataAccessMapper.warehouseEntityToWarehouse(
                warehouseJpaRepository.save(warehouseDataAccessMapper.warehouseToWarehouseEntity(warehouse)));
    }

    @Override
    public Optional<Warehouse> findById(WarehouseId warehouseId) {
        return warehouseJpaRepository.findById(warehouseId.getValue()).
                map(warehouseDataAccessMapper::warehouseEntityToWarehouse);
    }
}
