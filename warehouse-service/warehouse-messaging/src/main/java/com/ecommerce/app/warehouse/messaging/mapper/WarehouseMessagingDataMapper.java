package com.ecommerce.app.warehouse.messaging.mapper;

import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseCreateAvroModel;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMessagingDataMapper {

    public WarehouseCreateAvroModel warehouseCreatedEventToOtherRequestAvroModel(WarehouseCreatedEvent warehouseCreatedEvent) {
        Warehouse warehouse = warehouseCreatedEvent.getWarehouse();
        return WarehouseCreateAvroModel.newBuilder()
                .setWarehouseId(warehouse.getId().getValue())
                .build();
    }
}
