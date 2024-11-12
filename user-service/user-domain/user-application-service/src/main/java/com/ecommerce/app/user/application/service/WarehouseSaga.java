package com.ecommerce.app.user.application.service;

import com.ecommerce.app.common.domain.event.DomainEvent;
import com.ecommerce.app.common.domain.event.EmptyEvent;
import com.ecommerce.app.saga.SagaStep;
import com.ecommerce.app.user.application.service.dto.message.WarehouseCreate;
import com.ecommerce.app.user.domain.core.UserDomainService;
import com.ecommerce.app.user.domain.core.entity.Warehouse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class WarehouseSaga implements SagaStep<WarehouseCreate, EmptyEvent, EmptyEvent> {

    private final UserDomainService userDomainService;
    private final WarehouseSagaHelper warehouseSagaHelper;

    public WarehouseSaga(UserDomainService userDomainService, WarehouseSagaHelper warehouseSagaHelper) {
        this.userDomainService = userDomainService;
        this.warehouseSagaHelper = warehouseSagaHelper;
    }

    @Override
    @Transactional
    public EmptyEvent process(WarehouseCreate warehouseCreate) {
        Warehouse warehouse = warehouseSagaHelper.findWarehouseById(warehouseCreate.getWarehouseId());
        warehouseSagaHelper.saveWarehouse(warehouse);
        log.info("Warehouse saved with id " + warehouse.getId());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public EmptyEvent rollback(WarehouseCreate warehouseCreate) {
        return EmptyEvent.INSTANCE;
    }
}
