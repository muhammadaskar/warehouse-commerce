package com.ecommerce.app.warehouse.messaging.mapper;

import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel;
import com.ecommerce.app.warehouse.domain.core.entity.Warehouse;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMessagingDataMapper {

    public WarehouseRequestAvroModel warehouseCreatedEventToOtherRequestAvroModel(WarehouseCreatedEvent warehouseCreatedEvent) {
        Warehouse warehouse = warehouseCreatedEvent.getWarehouse();
        return WarehouseRequestAvroModel.newBuilder()
                .setWarehouseId(warehouse.getId().getValue())
                .setName(warehouse.getName())
                .build();
    }
}
