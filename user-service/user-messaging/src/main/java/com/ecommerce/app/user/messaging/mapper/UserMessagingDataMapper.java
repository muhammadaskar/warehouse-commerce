package com.ecommerce.app.user.messaging.mapper;

import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseCreateAvroModel;
import com.ecommerce.app.user.application.service.dto.message.WarehouseCreate;
import org.springframework.stereotype.Component;

@Component
public class UserMessagingDataMapper {
    public WarehouseCreate warehouseCreateAvroModelToWarehouseCreate(WarehouseCreateAvroModel warehouseCreateAvroModel) {
        return WarehouseCreate.builder().warehouseId(warehouseCreateAvroModel.getWarehouseId().toString()).build();
    }
}
