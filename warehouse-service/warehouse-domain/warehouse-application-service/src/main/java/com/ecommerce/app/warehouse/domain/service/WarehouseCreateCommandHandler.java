package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseResponse;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.warehouse.WarehouseCreatedRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarehouseCreateCommandHandler {
    private final WarehouseCreateCommandHelper warehouseCreateCommandHelper;
    private final WarehouseDataMapper warehouseDataMapper;
    private final WarehouseCreatedRequestMessagePublisher warehouseCreatedRequestMessagePublisher;

    public WarehouseCreateCommandHandler(WarehouseCreateCommandHelper warehouseCreateCommandHelper,
                                         WarehouseDataMapper warehouseDataMapper,
                                         WarehouseCreatedRequestMessagePublisher warehouseCreatedRequestMessagePublisher) {
        this.warehouseCreateCommandHelper = warehouseCreateCommandHelper;
        this.warehouseDataMapper = warehouseDataMapper;
        this.warehouseCreatedRequestMessagePublisher = warehouseCreatedRequestMessagePublisher;
    }

    public CreateWarehouseResponse createWarehouse(CreateWarehouseCommand createWarehouseCommand) {
        WarehouseCreatedEvent warehouseCreatedEvent = warehouseCreateCommandHelper.persistWarehouse(createWarehouseCommand);
        warehouseCreatedRequestMessagePublisher.publish(warehouseCreatedEvent);
        return warehouseDataMapper.warehouseToCreateWarehouseResponse(
                warehouseCreatedEvent.getWarehouse(), "warehouse created successfully"
        );
    }
}
