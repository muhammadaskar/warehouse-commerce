package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.core.WarehouseDomainService;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseCommand;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.warehouse.WarehouseCreatedRequestMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class WarehouseCreateCommandHelper {
    private final WarehouseDomainService warehouseDomainService;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseDataMapper warehouseDataMapper;
    private final WarehouseCreatedRequestMessagePublisher warehouseCreatedRequestMessagePublisher;

    public WarehouseCreateCommandHelper(WarehouseDomainService warehouseDomainService, WarehouseRepository warehouseRepository, WarehouseDataMapper warehouseDataMapper, WarehouseCreatedRequestMessagePublisher warehouseCreatedRequestMessagePublisher) {
        this.warehouseDomainService = warehouseDomainService;
        this.warehouseRepository = warehouseRepository;
        this.warehouseDataMapper = warehouseDataMapper;
        this.warehouseCreatedRequestMessagePublisher = warehouseCreatedRequestMessagePublisher;
    }

    @Transactional
    public WarehouseCreatedEvent persistWarehouse(CreateWarehouseCommand createWarehouseCommand) {
        Warehouse warehouse = warehouseDataMapper.warehouseCommandToWarehouse(createWarehouseCommand);
        WarehouseCreatedEvent warehouseCreatedEvent = warehouseDomainService
                .validateAndInitiateWarehouse(warehouse, warehouseCreatedRequestMessagePublisher);
        saveWarehouse(warehouse);
        log.info("Warehouse created with id: {}", warehouse.getId().getValue());
        return warehouseCreatedEvent;
    }

    private Warehouse saveWarehouse(Warehouse warehouse) {
        Warehouse warehouseResult = warehouseRepository.save(warehouse);
        if (warehouseResult == null) {
            log.error("could not save warehouse");
            throw new WarehouseDomainException("could not save warehouse");
        }
        return warehouse;
    }
}
