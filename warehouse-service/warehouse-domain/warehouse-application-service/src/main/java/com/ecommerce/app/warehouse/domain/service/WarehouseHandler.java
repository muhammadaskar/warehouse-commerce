package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseResponse;
import com.ecommerce.app.warehouse.domain.service.dto.create.WarehouseIdQuery;
import com.ecommerce.app.warehouse.domain.service.dto.create.WarehouseResponse;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.warehouse.WarehouseCreatedRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WarehouseHandler {
    private final WarehouseHelper warehouseHelper;
    private final WarehouseDataMapper warehouseDataMapper;
    private final WarehouseCreatedRequestMessagePublisher warehouseCreatedRequestMessagePublisher;

    public WarehouseHandler(WarehouseHelper warehouseHelper,
                            WarehouseDataMapper warehouseDataMapper,
                            WarehouseCreatedRequestMessagePublisher warehouseCreatedRequestMessagePublisher) {
        this.warehouseHelper = warehouseHelper;
        this.warehouseDataMapper = warehouseDataMapper;
        this.warehouseCreatedRequestMessagePublisher = warehouseCreatedRequestMessagePublisher;
    }

    public WarehouseResponse getWarehouseById(WarehouseIdQuery warehouseIdQuery) {
        return warehouseDataMapper.warehouseToWarehouseResponse(
                warehouseHelper.findWarehouseById(warehouseIdQuery)
        );
    }

    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseDataMapper.warehousesToWarehouseResponses(
                warehouseHelper.findAllWarehouses()
        );
    }

    public CreateWarehouseResponse createWarehouse(CreateWarehouseCommand createWarehouseCommand) {
        WarehouseCreatedEvent warehouseCreatedEvent = warehouseHelper.persistWarehouse(createWarehouseCommand);
        warehouseCreatedRequestMessagePublisher.publish(warehouseCreatedEvent);
        return warehouseDataMapper.warehouseToCreateWarehouseResponse(
                warehouseCreatedEvent.getWarehouse(), "warehouse created successfully"
        );
    }
}
