package com.ecommerce.app.warehouse.messaging.mapper;

import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WarehouseMessagingDataMapper {

    public WarehouseRequestAvroModel warehouseCreatedEventToOtherRequestAvroModel(WarehouseCreatedEvent warehouseCreatedEvent) {
        Warehouse warehouse = warehouseCreatedEvent.getWarehouse();
        return WarehouseRequestAvroModel.newBuilder()
                .setWarehouseId(UUID.randomUUID())
                .setName(warehouse.getName())
                .build();
    }
}
