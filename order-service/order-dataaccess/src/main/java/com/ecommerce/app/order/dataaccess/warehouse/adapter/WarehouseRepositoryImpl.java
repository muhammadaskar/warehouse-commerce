package com.ecommerce.app.order.dataaccess.warehouse.adapter;

import com.ecommerce.app.order.application.service.ports.output.WarehouseRepository;
import com.ecommerce.app.order.dataaccess.warehouse.mapper.WarehouseDataAccessMapper;
import com.ecommerce.app.order.dataaccess.warehouse.repository.WarehouseJpaRepository;
import com.ecommerce.app.order.domain.core.entity.Warehouse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WarehouseRepositoryImpl implements WarehouseRepository {

    private final WarehouseJpaRepository warehouseJpaRepository;
    private final WarehouseDataAccessMapper warehouseDataAccessMapper;

    public WarehouseRepositoryImpl(WarehouseJpaRepository warehouseJpaRepository, WarehouseDataAccessMapper warehouseDataAccessMapper) {
        this.warehouseJpaRepository = warehouseJpaRepository;
        this.warehouseDataAccessMapper = warehouseDataAccessMapper;
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouseJpaRepository.findAll().stream()
                .map(warehouseDataAccessMapper::warehouseEntityToWarehouse)
                .toList();
    }

    @Override
    public List<Warehouse> findAllWithProducts() {
        return warehouseJpaRepository.findAll().stream()
                .map(warehouseDataAccessMapper::warehouseEntityToWarehouse)
                .toList();
    }

    @Override
    public List<Warehouse> findAllWithProductsAndStocks() {
        return warehouseJpaRepository.findAll().stream()
                .map(warehouseDataAccessMapper::warehouseEntityToWarehouse)
                .toList();
    }
}
