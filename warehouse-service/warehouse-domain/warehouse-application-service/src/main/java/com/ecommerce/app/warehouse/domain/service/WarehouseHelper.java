package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.WarehouseDomainService;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseNotFoundException;
import com.ecommerce.app.warehouse.domain.service.dto.create.CreateWarehouseCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.WarehouseIdQuery;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.warehouse.WarehouseCreatedRequestMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class WarehouseHelper {
    private final WarehouseDomainService warehouseDomainService;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseDataMapper warehouseDataMapper;
    private final WarehouseCreatedRequestMessagePublisher warehouseCreatedRequestMessagePublisher;

    public WarehouseHelper(WarehouseDomainService warehouseDomainService,
                           WarehouseRepository warehouseRepository,
                           WarehouseDataMapper warehouseDataMapper,
                           WarehouseCreatedRequestMessagePublisher warehouseCreatedRequestMessagePublisher) {
        this.warehouseDomainService = warehouseDomainService;
        this.warehouseRepository = warehouseRepository;
        this.warehouseDataMapper = warehouseDataMapper;
        this.warehouseCreatedRequestMessagePublisher = warehouseCreatedRequestMessagePublisher;
    }

    /**
     * Find warehouse by warehouseId
     * @param warehouseIdQuery WarehouseIdQuery
     * @return Warehouse
     */
    @Transactional(readOnly = true)
    public Warehouse findWarehouseById(WarehouseIdQuery warehouseIdQuery) {
        return findWarehouse(new WarehouseId(warehouseIdQuery.getWarehouseId()));
    }

    /**
     * Find all warehouses
     * @return List<Warehouse>
     */
    @Transactional(readOnly = true)
    public List<Warehouse> findAllWarehouses() {
        return findAllWarehouse();
    }

    /**
     * Create a warehouse
     * @param createWarehouseCommand CreateWarehouseCommand
     * @return WarehouseCreatedEvent
     */
    @Transactional
    public WarehouseCreatedEvent persistWarehouse(CreateWarehouseCommand createWarehouseCommand) {
        Warehouse warehouse = warehouseDataMapper.warehouseCommandToWarehouse(createWarehouseCommand);
        WarehouseCreatedEvent warehouseCreatedEvent = warehouseDomainService
                .validateAndInitiateWarehouse(warehouse, warehouseCreatedRequestMessagePublisher);
        saveWarehouse(warehouseCreatedEvent.getWarehouse());
        log.info("Warehouse created with id: {}", warehouse.getId().getValue());

        return warehouseCreatedEvent;
    }

    /**
     * Find all warehouses
     * @return List<Warehouse>
     */
    @Transactional(readOnly = true)
    public List<Warehouse> findAllWarehouse() {
        return warehouseRepository.findAll();
    }

    /**
     * Find warehouse by warehouseId
     * @param warehouseId WarehouseId
     * @return Warehouse
     */
    public Warehouse findWarehouse(WarehouseId warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
    }

    /**
     * Save warehouse, this method is used to save warehouse
     * @param warehouse Warehouse
     * @return Warehouse
     */
    private Warehouse saveWarehouse(Warehouse warehouse) {
        Warehouse warehouseResult = warehouseRepository.save(warehouse);
        if (warehouseResult == null) {
            log.error("could not save warehouse");
            throw new WarehouseDomainException("could not save warehouse");
        }
        return warehouse;
    }
}
