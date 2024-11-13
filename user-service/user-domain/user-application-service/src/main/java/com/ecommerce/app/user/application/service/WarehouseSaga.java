package com.ecommerce.app.user.application.service;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.saga.SagaStep;
import com.ecommerce.app.user.application.service.dto.message.WarehouseCreate;
import com.ecommerce.app.user.application.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.user.domain.core.UserDomainService;
import com.ecommerce.app.user.domain.core.entity.Warehouse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarehouseSaga implements SagaStep<WarehouseCreate, DomainEvent, DomainEvent> {

    private final UserDomainService userDomainService;
    private final WarehouseSagaHelper warehouseSagaHelper;
    private final WarehouseDataMapper warehouseDataAccessMapper;

    public WarehouseSaga(UserDomainService userDomainService, WarehouseSagaHelper warehouseSagaHelper, WarehouseDataMapper warehouseDataAccessMapper) {
        this.userDomainService = userDomainService;
        this.warehouseSagaHelper = warehouseSagaHelper;
        this.warehouseDataAccessMapper = warehouseDataAccessMapper;
    }

    @Override
    public DomainEvent process(WarehouseCreate data) {
        log.info("Processing warehouse saga step");
        Warehouse warehouse = warehouseDataAccessMapper.warehouseCreateToWarehouse(data);
        warehouseSagaHelper.saveWarehouse(warehouse);
        log.info("Warehouse with id: {} is created", warehouse.getId().getValue());
        return null;
    }

    @Override
    public DomainEvent rollback(WarehouseCreate data) {
        return null;
    }
}
