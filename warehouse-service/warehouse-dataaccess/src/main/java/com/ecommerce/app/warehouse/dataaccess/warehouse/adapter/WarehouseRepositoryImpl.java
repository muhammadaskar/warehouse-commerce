package com.ecommerce.app.warehouse.dataaccess.warehouse.adapter;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.dataaccess.warehouse.mapper.WarehouseDataAccessMapper;
import com.ecommerce.app.warehouse.dataaccess.warehouse.repository.WarehouseJpaRepository;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
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
        return warehouseDataAccessMapper.warehouseEntityToWarehouseEntity(
                warehouseJpaRepository.save(warehouseDataAccessMapper.warehouseToWarehouseEntity(warehouse))
        );
    }

    @Override
    public Optional<Warehouse> findById(WarehouseId warehouseId) {
        return warehouseJpaRepository.findById(warehouseId.getValue()).map(warehouseDataAccessMapper::warehouseEntityToWarehouseEntityWithStocks);
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouseJpaRepository.findAll().stream().
                map(warehouseDataAccessMapper::warehouseEntityToWarehouseEntityWithStocks).toList();
    }
}
