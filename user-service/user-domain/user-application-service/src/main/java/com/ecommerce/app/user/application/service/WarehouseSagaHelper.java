package com.ecommerce.app.user.application.service;

import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.user.application.service.ports.output.repository.WarehouseRepository;
import com.ecommerce.app.user.domain.core.entity.Warehouse;
import com.ecommerce.app.user.domain.core.exception.WarehouseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class WarehouseSagaHelper {
    private final WarehouseRepository warehouseRepository;

    public WarehouseSagaHelper(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    Warehouse findWarehouseById(String warehouseId) {
        Optional<Warehouse> warehouseResponse = warehouseRepository.findByWarehouseId(new WarehouseId(UUID.fromString(warehouseId)));
        if (warehouseResponse.isPresent()) {
            log.error("Warehouse with id {} is present", warehouseId);
            throw new WarehouseException("Warehouse with id " + warehouseId + " is present");
        }
        return warehouseResponse.get();
    }

    void saveWarehouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }
}
